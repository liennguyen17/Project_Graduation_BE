package com.ltw.users;

import com.ltw.dto.entity.users.UserDTO;
import com.ltw.dto.request.user.CreateUserRequest;
import com.ltw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TestUsersService {
    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        try {
            CreateUserRequest request = new CreateUserRequest();
            request.setName("Nguyen Ngoc Nam");
            request.setEmail("nguyenNam@gmail.com");
            request.setUsername("ngocnam02");
            request.setDob("22/12/2009");
            request.setSubject("cong nghe thong tin");
            request.setAddress("BAC NINH");
            request.setPhone("0988877650");
            request.setPassword("123456@a");
            request.setRoleId(1);
            UserDTO userDTO = userService.createUser(request);
            log.info("Create user success: {}", userDTO);
        } catch (Exception ex) {

            log.error("Loi tao usser: {}", ex.getMessage());
        }
    }
}
