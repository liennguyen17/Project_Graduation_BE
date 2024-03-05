package com.ltw.service;

import com.ltw.constant.Constants;
import com.ltw.dto.entity.users.UserDTO;
import com.ltw.dto.entity.users.UserDeleteDTO;
import com.ltw.dto.entity.users.UserUpdateValueDto;
import com.ltw.dto.request.user.CreateUserRequest;
import com.ltw.dto.request.user.DeleteUsersRequest;
import com.ltw.dto.request.user.UpdateUserRequest;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.Role;
import com.ltw.model.User;
import com.ltw.repository.roles.RolesRepository;
import com.ltw.repository.users.CustomUserQuery;
import com.ltw.repository.users.UsersRepository;
import com.ltw.service.mapper.UserUpdateMapper;
import com.ltw.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("UserService")
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final RolesRepository roleRepository;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final UserUpdateMapper userUpdateMapper;
    public UserDTO createUser(CreateUserRequest request){
        try{
            checkUserIsExitByUsername(request.getUsername(), null);
//            checkRoleIsValid(request.getRoleId());
//            Optional<Role> roleOptional = roleRepository.findById(request.getRoleId());
            User user = User.builder()
                    .username(request.getUsername())
                    .name(request.getName())
                    .dob(DateUtils.convertDateFromString(request.getDob(), Constants.DateTimeFormatConstant.DATE_FORMAT))
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .subject(request.getSubject())
                    .password(request.getPassword())
                    .createAt(new Timestamp(System.currentTimeMillis()))
                    .updateAt(new Timestamp(System.currentTimeMillis()))
                    .role(request.getRole())
                    .build();
//            user.setRoleId(roleOptional.get());
            user = usersRepository.saveAndFlush(user);
            return modelMapper.map(user, UserDTO.class);
        } catch (Exception e) {
            throw new DataNotFoundException("Quá trình tạo người dùng không thành công ! " + e.getMessage());
        }
    }

    public UserDTO updateUser(UpdateUserRequest request){
        Optional<User> user = usersRepository.findById(request.getId());
        if(!user.isPresent()){
            throw new DataNotFoundException(Constants.ErrorMessageUserValidation.NOT_FIND_USER_BY_ID + request.getId());
        }
        checkUserIsExitByUsername(request.getUsername(), request.getId());
        checkRoleIsValid(request.getRoleId());
        UserUpdateValueDto updateValueDto = modelMapper.map(request, UserUpdateValueDto.class);
        User updateValue = user.get();
        userUpdateMapper.updateUserFromDto(updateValueDto, updateValue);
        updateValue.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return modelMapper.map(usersRepository.saveAndFlush(updateValue), UserDTO.class);
    }

    public List<ErrorDetail> deleteUsers(DeleteUsersRequest request){
        log.info("deleteUser: {}", request.getIds());
        List<UserDeleteDTO> users = usersRepository.findAllById(request.getIds()).stream()
                .map(u -> modelMapper.map(u, UserDeleteDTO.class)).toList();
        List<ErrorDetail> errorDetails = new ArrayList<>();
        for(Integer requesId: request.getIds()){
            boolean isExit = users.stream().anyMatch(u -> u.getId().equals(requesId));
            if(!isExit){
                ErrorDetail errorDetail = new ErrorDetail();
                errorDetail.setId(requesId.toString());
                errorDetail.setMessage(Constants.ErrorMessageUserValidation.NOT_FIND_USER_BY_ID + request.getIds());
                errorDetails.add(errorDetail);
            }
        }

        if(errorDetails.isEmpty()){
            usersRepository.deleteAllById(request.getIds());
            return null;
        }
        return errorDetails;
    }

//    public List<UserDTO> deleteUsers(DeleteUsersRequest request){
//        try {
//            List<UserDTO> userDTOS = new ArrayList<>();
//            for (User user : usersRepository.findAllById(request.getIds())) {
//                userDTOS.add(modelMapper.map(user, UserDTO.class));
//            }
//            usersRepository.deleteAllByIdInBatch(request.getIds());
//            return userDTOS;
//        }catch (Exception e){
//            throw new DataNotFoundException("Có lỗi xảy ra trong quá trình xóa" + e.getMessage());
//        }
//    }

    public UserDTO getUserById(Integer id){
        Optional<User> user = usersRepository.findById(id);
        if(!user.isPresent()){
            throw new DataNotFoundException(Constants.ErrorMessageUserValidation.NOT_FIND_USER_BY_ID + id);
        }
        return modelMapper.map(user.get(), UserDTO.class);
    }

    public Page<User> getUserByParam(CustomUserQuery.UserFilterParam param, PageRequest request){
        Specification<User> specification = CustomUserQuery.getFilterUser(param);
        return usersRepository.findAll(specification, request);
    }

    private void checkUserIsExitByUsername(String name, Integer id){
        if(id == null && usersRepository.existsAllByUsername(name)){
            throw new DataNotFoundException("Tên đăng nhập đã tồn tại!");
        }
        if(id!= null && usersRepository.existsAllByUsernameAndIdNot(name, id))
            throw new DataNotFoundException("Tên đăng nhập đã tồn tại!");
    }

    private Role buildRole(Integer id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if(roleOptional.isEmpty()){
            throw new DataNotFoundException("Vai trò không tồn tại.");
        }
//        return roleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Vai trò không tồn tại."));
        return roleOptional.get();
    }

    private void checkRoleIsValid(Integer roleId) {
        if (roleId == null) {
            return;
        }
        Role role = buildRole(roleId);
        if (role == null) {
            throw new DataNotFoundException("Vai trò không tồn tại");
        }
    }
}
