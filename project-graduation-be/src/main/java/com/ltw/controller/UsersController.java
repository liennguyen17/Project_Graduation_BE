package com.ltw.controller;

import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.entity.users.UserDTO;
import com.ltw.dto.request.user.*;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGE','TEACHER')")
    public BaseItemResponse<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request){
        return BaseResponse.successData(userService.createUser(request));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGE','TEACHER','STUDENT')")
    public BaseItemResponse<UserDTO> updateUser(@Valid @RequestBody UpdateUserRequest request){
        return BaseResponse.successData(userService.updateUser(request));
    }

    @DeleteMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public BaseResponse deleteUsers1(@Valid @RequestBody DeleteUsersRequest request) {
        List<ErrorDetail> errorDetailList = userService.deleteUsers(request);
        if (errorDetailList == null) {
            return BaseResponse.successData("Xóa người dùng thành công");
        }
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION, ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_VALIDATION), errorDetailList);
    }
//    @DeleteMapping
//    public BaseListResponse<UserDTO> deleteUsers(@RequestBody @Valid DeleteUsersRequest request) {
//        List<UserDTO> userDTOS = userService.deleteUsers(request);
//        return BaseListResponse.successListData(userDTOS, userDTOS.size());
//
//    }

    @GetMapping("{id}")
    public BaseItemResponse<UserDTO> getUserById(@PathVariable("id") Integer id){
        return BaseResponse.successData(userService.getUserById(id));
    }

    @PostMapping("/filter")
    public BaseListResponse<UserDTO> filterUser(@Valid @RequestBody GetUserRequest request) {
        Page<User> userPage = userService.getUserByParam(request, PageRequest.of(request.getStart(), request.getLimit()));
        return BaseResponse.successListData(userPage.getContent().stream()
                .map(e -> modelMapper.map(e,UserDTO.class)).collect(Collectors.toList()), (int)userPage.getTotalElements());
    }

    @GetMapping("/profile")
    public BaseItemResponse<UserDTO> getUserProfile() {
        return BaseResponse.successData(userService.getUserProfile());
    }

    @PostMapping("/change/password")
    @PreAuthorize("hasAnyAuthority('ADMIN','DEPARTMENT_HEAD','ASSISTANT','TEACHER','STUDENT')")
    public BaseResponse changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return BaseResponse.successData("Mật khẩu đã được thay đổi thành công!");
    }

    @PostMapping("/forgot/password")
    public BaseResponse forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request);
        return BaseResponse.successData("Quá trình thực hiện chức năng quên mật khẩu thành công");
    }

    @GetMapping("/{role}")
    public BaseItemResponse<UserDTO> findUserByRole(@PathVariable String role) {
        return BaseResponse.successData(userService.find_UserByRole(role));
    }
}
