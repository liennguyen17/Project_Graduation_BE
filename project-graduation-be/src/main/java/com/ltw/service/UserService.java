package com.ltw.service;

import com.ltw.constant.Constants;
import com.ltw.dto.entity.topic.TopicDTO;
import com.ltw.dto.entity.topic.TopicStudentDTO;
import com.ltw.dto.entity.topic.TopicTeacherDTO;
import com.ltw.dto.entity.users.UserDTO;
import com.ltw.dto.entity.users.UserDeleteDTO;
import com.ltw.dto.entity.users.UserUpdateValueDto;
import com.ltw.dto.request.user.*;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.email.EmailService;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.Role;
import com.ltw.model.Topic;
import com.ltw.model.User;
import com.ltw.repository.roles.RolesRepository;
import com.ltw.repository.topic.TopicRepository;
import com.ltw.repository.users.CustomUserQuery;
import com.ltw.repository.users.UsersRepository;
import com.ltw.service.mapper.UserUpdateMapper;
import com.ltw.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service("UserService")
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final RolesRepository roleRepository;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final UserUpdateMapper userUpdateMapper;
    private final PasswordEncoder encoder;
    private final TopicRepository topicRepository;
    @Autowired
    private EmailService emailService;
    public UserDTO createUser(CreateUserRequest request){
        try{
            checkUserIsExitByUsername(request.getUsername(), null);
            checkUserCode(request.getUserCode(), null);
            User user = User.builder()
                    .username(request.getUsername())
                    .name(request.getName())
                    .dob(DateUtils.convertDateFromString(request.getDob(), Constants.DateTimeFormatConstant.FORMAT_DATE))
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .subject(request.getSubject())
                    .password(encoder.encode(request.getPassword()))
                    .createAt(new Timestamp(System.currentTimeMillis()))
                    .updateAt(new Timestamp(System.currentTimeMillis()))
                    .role(request.getRole())
                    .userCode(request.getUserCode())
                    .className(request.getClassName())
                    .build();
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
        checkUserCode(request.getUserCode(), request.getId());
//        checkRoleIsValid(request.getRoleId());
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

    private void checkUserCode(String userCode, Integer id){
        if(id == null && usersRepository.existsAllByUserCode(userCode)){
            throw new DataNotFoundException("Mã người dùng đã tồn tại");
        }
        if(id!=null && usersRepository.existsAllByUserCodeAndIdNot(userCode, id)){
            throw new DataNotFoundException("Mã người dùng đã tồn tại");
        }
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

    public UserDTO getUserProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInUsername = authentication.getName();

            User loggedUser = usersRepository.getUserByUsername(loggedInUsername);

            if (loggedUser != null) {
                UserDTO userDTO = modelMapper.map(loggedUser, UserDTO.class);
                return userDTO;
            }
        } catch (Exception ex) {
            throw new DataNotFoundException("Không tìm thấy thông tin user");
        }
        throw new DataNotFoundException("Có lỗi xảy ra trong quá trình lấy thông tin người dùng");
    }

    public void changePassword(ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Optional<User> userOptional = usersRepository.findByUsername(loggedInUsername);
        if (!userOptional.isPresent()) {
            throw new DataNotFoundException(Constants.ErrorMessageUserValidation.USER_NOT_EXIST);
        }
        User user = userOptional.get();
        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new DataNotFoundException(Constants.ErrorMessageUserValidation.PASSWORD_OLD_NOT_CORRECT);
        }
        if (!isValidPassword(request.getNewPassword())) {
            throw new DataNotFoundException(Constants.ErrorMessageUserValidation.PASSWORD_NEW_NOT_VALID);
        }
        if (!request.getConfirmNewPassword().equals(request.getNewPassword())) {
            throw new DataNotFoundException(Constants.ErrorMessageUserValidation.PASSWORD_NOT_MATCH);
        }
        if (encoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new DataNotFoundException(Constants.ErrorMessageUserValidation.PASSWORD_DIFFERENT);
        }

        String newPasswordEncoded = encoder.encode(request.getNewPassword());
        user.setPassword(newPasswordEncoded);
        usersRepository.saveAndFlush(user);
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 6) {
            throw new DataNotFoundException("Mật khẩu phải có ít nhất 6 ký tự!");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new DataNotFoundException("Mật khẩu phải chưa ít nhất một chữ hoa!");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new DataNotFoundException("Mật khẩu phải chưa ít nhất một chữ thường!");
        }
        if (!password.matches(".*\\d.*")) {
            throw new DataNotFoundException("Mật khẩu phải chứa ít nhất một chữ số.");
        }
        if (!password.matches(".*[@#$%^&+=].*")) {
            throw new DataNotFoundException("Mật khẩu phải chứa ít nhất một ký tự đặc biệt.");
        }
        return true;
    }


    public void forgotPassword(ForgotPasswordRequest request) {
        User user = usersRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new DataNotFoundException("Không tìm thấy người dùng ứng với địa chỉ email này!");
        }
        String newPassword = generateVerificationCode();
        user.setPassword(encoder.encode(newPassword));
        usersRepository.saveAndFlush(user);

        String emailContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Quên mật khẩu</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "        }\n" +
                "        .container {\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                "            margin: 0 auto;\n" +
                "            max-width: 600px;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #41e2dd;\n" +
                "            border-radius: 5px 5px 0 0;\n" +
                "            color: #000000;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .code {\n" +
                "            background-color: #bdf9f1;\n" +
                "            border: 2px solid #3d3f3f;\n" +
                "            border-radius: 4px;\n" +
                "            color: #000000;\n" +
                "            display: inline-block;\n" +
                "            font-size: 18px;\n" +
                "            margin-top: 10px;\n" +
                "            padding: 10px 15px;\n" +
                "            margin: 10px auto;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            background-color: #f4f4f4;\n" +
                "            border-radius: 0 0 5px 5px;\n" +
                "            padding: 10px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h2>Quên mật khẩu</h2>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Chào bạn!</p>\n" +
                "            <p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu của bạn.</p>\n" +
                "            <p>Đây là mã xác nhận của bạn:</p>\n" +
                "            <div class=\"code\">" + newPassword + "</div>\n" +
                "            <p>Nhập mã để đăng nhập vào tài khoản vào hệ thống đăng ký khóa luận tốt nghiệp của bạn !</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Trân trọng,</p>\n" +
                "            <p>Học viện nông nghiệp Việt Nam</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";


        emailService.sendEmailHtml(user.getEmail(), newPassword + " là mã khôi phục tài khoản hệ thống khóa luận tốt nghiệp của bạn !\n", emailContent);

    }

    public List<TopicTeacherDTO> getTopicsForTeacher(){
        Optional<User> currentUserOptional = getCurrentUser();
        if(currentUserOptional.isPresent()){
            User currentUser = currentUserOptional.get();
            if(currentUser.getRole().equals("TEACHER")){
                List<Topic> topics = topicRepository.findByTeacher(currentUser);
                List<TopicTeacherDTO> topicDTOS = new ArrayList<>();
                for (Topic topic : topics){
                    TopicTeacherDTO topicDTO = modelMapper.map(topic, TopicTeacherDTO.class);
                    topicDTOS.add(topicDTO);
                }
                return topicDTOS;
            } else {
                throw new DataNotFoundException("Người dùng không phải là giáo viên.");
            }
        }else {
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng đăng nhập.");
        }
    }

    public TopicStudentDTO getTopicForStudent(){
        Optional<User> currentUserOptional = getCurrentUser();
        if(currentUserOptional.isPresent()){
            User currentUser = currentUserOptional.get();
            if(currentUser.getRole().equals("STUDENT")){
                Topic topic = topicRepository.findByStudent(currentUser);
                return modelMapper.map(topic, TopicStudentDTO.class);
            } else {
                throw new DataNotFoundException("Người dùng không phải là sinh viên.");
            }
        } else {
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng đăng nhập.");
        }
    }


    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public User findUserByRole(String role) {
        User user = usersRepository.findByRole(role);
        if (user == null) {
            throw new DataNotFoundException("Không tìm thấy người dùng với vai trò là " + role);
        }
        return user;
    }

    public UserDTO find_UserByRole(String role) {
        User user = usersRepository.findByRole(role);
        if (user == null) {
            throw new DataNotFoundException("Không tìm thấy người dùng với vai trò là " + role);
        }
        return modelMapper.map(user, UserDTO.class);
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return usersRepository.findByUsername(currentUsername);
    }

    public User findUserById(Integer userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("Không tìm thấy user có id: " + userId));
    }

    public User ensureUserHasRole(Integer id, String role) {
        User user = findUserById(id);
        if (!user.getRole().equals(role)) {
            throw new DataNotFoundException("Nguời dùng không có vai trò là: " + role);
        }
        return user;
    }

    public User findTeacher(Integer id){
        return ensureUserHasRole(id, "TEACHER");
    }

    public User findStudent(Integer id){
        return ensureUserHasRole(id, "STUDENT");
    }
}
