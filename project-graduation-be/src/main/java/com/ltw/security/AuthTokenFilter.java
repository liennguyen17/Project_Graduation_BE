package com.ltw.security;

import com.ltw.service.auth.JwtTokenProvider;
import io.netty.handler.codec.http.HttpMethod;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${public-url}")
    private String publicUrlconfig;

    @Value("${public-url-get}")
    private String publicUrlGetConfig;

    @Value("${public-url-post}")
    private String publicUrlPostConfig;

    private List<Pattern> publicUrlPatterns;
    private List<Pattern> publicUrlGetPatterns;
    private List<Pattern> publicUrlPostPatterns;

    public AuthTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostConstruct
    public void init(){
        String[] publicArray = publicUrlconfig.split(",");
        publicUrlPatterns = Arrays.stream(publicArray)
                .map(Pattern::compile)
                .toList();

        String[] publicUrlGetArray = publicUrlGetConfig.split(",");
        publicUrlGetPatterns = Arrays.stream(publicUrlGetArray)
                .map(Pattern::compile)
                .toList();

        String[] publicUrlPostArray = publicUrlPostConfig.split(",");
        publicUrlPostPatterns = Arrays.stream(publicUrlPostArray)
                .map(Pattern::compile)
                .toList();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (checkUrl(request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }

            if(request.getMethod().equals(HttpMethod.GET.name()) && checkUrlGet(request.getRequestURI())){
                filterChain.doFilter(request, response);
                return;
            }

            if(request.getMethod().equals(HttpMethod.POST.name()) && checkUrlPost(request.getRequestURI())){
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = parseJwt(request);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateJwtToken(jwt)) {
                UsernamePasswordAuthenticationToken authenticationToken = jwtTokenProvider.getUserInfoFromJWT(jwt);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


        } catch (Exception ex) {
            throw ex;
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }


    public boolean checkUrl(String requestUrl) {
        for(Pattern publicUrlPattern : publicUrlPatterns){
            if(publicUrlPattern.matcher(requestUrl).matches()){
                return true;
            }
        }
        return false;
    }

    public boolean checkUrlGet(String requestUrlGet){
        for(Pattern publicUrlGetPattern : publicUrlGetPatterns){
            if(publicUrlGetPattern.matcher(requestUrlGet).matches()){
                return true;
            }
        }
        return false;
    }

    public boolean checkUrlPost(String requestUrlPost){
        for(Pattern publicUrlPostPattern : publicUrlPostPatterns){
            if(publicUrlPostPattern.matcher(requestUrlPost).matches()){
                return true;
            }
        }
        return false;
    }
}
