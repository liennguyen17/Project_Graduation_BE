package com.ltw.controller;

import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.entity.users.UserDTO;
import com.ltw.dto.request.user.CreateUserRequest;
import com.ltw.dto.request.user.DeleteUsersRequest;
import com.ltw.dto.request.user.GetUserRequest;
import com.ltw.dto.request.user.UpdateUserRequest;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseListResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.model.User;
import com.ltw.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public BaseItemResponse<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request){
        return BaseResponse.successData(userService.createUser(request));
    }

    @PutMapping
    public BaseItemResponse<UserDTO> updateUser(@Valid @RequestBody UpdateUserRequest request){
        return BaseItemResponse.successData(userService.updateUser(request));
    }

    @DeleteMapping
    public BaseResponse deleteUsers(@Valid @RequestBody DeleteUsersRequest request){
        List<ErrorDetail> errorDetailList = userService.deleteUsers(request);
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION, ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_VALIDATION), errorDetailList);
    }

    @GetMapping("{id}")
    public BaseItemResponse<UserDTO> getUserById(@PathVariable("id") Integer id){
        return BaseResponse.successData(userService.getUserById(id));
    }

    @PostMapping("/filter")
    public BaseListResponse<UserDTO> filter(@Valid @RequestBody GetUserRequest request){
        Page<User> userPage = userService.getUserByParam(request, PageRequest.of(request.getStart(), request.getLimit()));
        return BaseResponse.successListData(userPage.getContent().stream()
                .map(e -> modelMapper.map(e,UserDTO.class)).collect(Collectors.toList()), (int)userPage.getTotalElements());
    }
}
