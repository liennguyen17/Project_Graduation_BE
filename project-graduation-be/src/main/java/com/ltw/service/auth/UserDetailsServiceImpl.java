package com.ltw.service.auth;

import com.ltw.dto.UserDetailsImpl;
import com.ltw.model.User;
import com.ltw.repository.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UsersRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        return UserDetailsImpl.builder()
                .userName(user.getUsername())
                .id(user.getId())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }
}
