package com.ltw.service.auth;

import com.ltw.dto.entity.users.UserDTO;
import com.ltw.model.User;
import com.ltw.repository.users.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DetailsUserNameService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    @Value("${jwt.jwtSecret}")
    private String jwtSecret;

    @Autowired
    public DetailsUserNameService(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        Integer userId = Integer.parseInt(claims.getSubject());
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return modelMapper.map(user, UserDTO.class);
    }

}
