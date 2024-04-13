package com.ltw.security;

import com.ltw.config.CorsConfigFilter;
import com.ltw.service.auth.JwtTokenProvider;
import io.netty.handler.codec.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final UserDetailsService myUserDetailService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfigFilter corsConfigFilter;

    @Autowired
    public WebSecurityConfig(UserDetailsService myUserDetailService, AuthEntryPointJwt unauthorizedHandler, JwtTokenProvider jwtTokenProvider, CorsConfigFilter corsConfigFilter) {
        this.myUserDetailService = myUserDetailService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtTokenProvider = jwtTokenProvider;
        this.corsConfigFilter = corsConfigFilter;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtTokenProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(unauthorizedHandler)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                .requestMatchers("/auth/**").permitAll()
//                .requestMatchers("/users/auth/**").permitAll()
//                .requestMatchers("/file/**").permitAll()
//                .requestMatchers("users/forgot/password").permitAll()
//                .requestMatchers("/news/**").permitAll()
//                .requestMatchers("/master-data/filter").permitAll()
//                .requestMatchers(request -> {
//                    if (request.getMethod().equals(HttpMethod.POST.toString())) {
//                        return new RegexRequestMatcher("/(users|topic|notifications|news|master-data...)/filter", null).matches(request);
//                    }
//                    return false;
//                }).permitAll()
//                .requestMatchers(request -> {
//                    if (request.getMethod().equals(HttpMethod.GET.toString())) {
//                        return new RegexRequestMatcher("/(users|topic|notifications|news|master-data...)/(\\d+)", null).matches(request);
//                    }
//                    return false;
//                }).permitAll()
//                .anyRequest().authenticated();
//        http.authenticationProvider(authenticationProvider());
//        http.addFilterBefore(corsConfigFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList("*"));
                    configuration.setAllowedMethods(Arrays.asList("*"));
                    configuration.setAllowedHeaders(Arrays.asList("*"));
                    return configuration;
                }))
                .csrf().disable()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/file/**").permitAll()
                .requestMatchers("/users/forgot/password").permitAll()
                .requestMatchers("/master-data/**").permitAll()
                .requestMatchers("/comments/all").permitAll()
                .requestMatchers(request -> {
                    if (request.getMethod().equals(HttpMethod.POST.toString())) {
                        return new RegexRequestMatcher("/(users|topic|notifications|news|master-data|data...)/filter", null).matches(request);
                    }
                    return false;
                }).permitAll()
                .requestMatchers(request -> {
                    if (request.getMethod().equals(HttpMethod.GET.toString())) {
                        return new RegexRequestMatcher("/(users|topic|notifications|news|master-data|comments|data...)/(all|\\d+)", null).matches(request);
                    }
                    return false;
                }).permitAll()
//                .anyRequest().authenticated();
                .anyRequest().permitAll();
        return http.build();
    }


}
