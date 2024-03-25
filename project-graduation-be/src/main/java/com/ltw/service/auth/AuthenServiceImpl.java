package com.ltw.service.auth;

import com.ltw.constant.Constants;
import com.ltw.dto.UserDetailsImpl;
import com.ltw.dto.request.auth.LoginRequest;
import com.ltw.dto.request.auth.RegisterRequest;
import com.ltw.dto.response.LoginResponse;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.User;
import com.ltw.repository.users.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthenServiceImpl implements AuthenService{
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AuthenServiceImpl(AuthenticationManager authenticationManager, UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateTokenWithAuthorities(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();




            return new LoginResponse(jwt,
                    userDetails.getId(),
                    userDetails.getName(),
                    userDetails.getUsername(),
                    userDetails.getPhone(),
                    userDetails.getEmail(),
                    roles);
        }catch (BadCredentialsException e){
            throw new DataNotFoundException("Sai mật khẩu!");
        }catch (Exception e){
            throw new DataNotFoundException("Thông tin đăng nhập sai!");
        }


    }


    @Override
    public void registerUser(RegisterRequest signUpRequest) {
        if(usersRepository.existsAllByUsername(signUpRequest.getUsername())){
            throw new DataNotFoundException( Constants.AuthValidation.USER_NAME_ALREADY_EXISTED);
        }
        if(usersRepository.existsAllByEmail(signUpRequest.getEmail())){
            throw new DataNotFoundException( Constants.AuthValidation.EMAIL_ALREADY_EXISTED);
        }
        User user = User.builder()
                .name(signUpRequest.getName())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phone(signUpRequest.getPhone())
                .role(signUpRequest.getRole())
                .build();
        usersRepository.saveAndFlush(user);

    }
}
