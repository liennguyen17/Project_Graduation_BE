package com.ltw.service;

import com.ltw.constant.Constants;
import com.ltw.dto.entity.topic.TopicDTO;
import com.ltw.dto.entity.topic.TopicDTO1;
import com.ltw.dto.entity.topic.TopicDeleteDTO;
import com.ltw.dto.request.topic.CreateTopicRequest;
import com.ltw.dto.request.topic.DeleteTopicRequest;
import com.ltw.dto.request.topic.StudentCreateTopicRequest;
import com.ltw.dto.request.topic.UpdateTopicRequest;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.Topic;
import com.ltw.model.User;
import com.ltw.repository.topic.CustomTopicQuery;
import com.ltw.repository.topic.TopicRepository;
import com.ltw.repository.users.UsersRepository;
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

@Service("TopicService")
@RequiredArgsConstructor
@Slf4j
public class TopicService {
    private final UsersRepository usersRepository;
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public TopicDTO createTopic(CreateTopicRequest request) {
        Topic topic = modelMapper.map(request, Topic.class);
        User teacher = userService.findTeacher(request.getTeacher());
        User student = userService.findStudent(request.getStudent());

        topic.setNameTopic(request.getNameTopic());
        topic.setSemester(request.getSemester());
        topic.setStatus(request.getStatus());
        topic.setDepartmentManagement(request.getDepartmentManagement());
        topic.setMenterInternshipFacility(request.getMenterInternshipFacility());
        topic.setNameInternshipFacility(request.getNameInternshipFacility());
        topic.setPhoneInstructorInternshipFacility(request.getPhoneInstructorInternshipFacility());
        topic.setTeacher(teacher);
        topic.setStudent(student);
        topic.setInstructor(request.getInstructor());
        topic.setReviewer(request.getReviewer());
        topic.setBoardMembers1(request.getBoardMembers1());
        topic.setBoardMembers2(request.getBoardMembers2());
        topic.setBoardMembers3(request.getBoardMembers3());
        topic.setCreateAt(new Timestamp(System.currentTimeMillis()));
        topic.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        Topic savedTopic = topicRepository.saveAndFlush(topic);
        return modelMapper.map(savedTopic, TopicDTO.class);
    }

    public TopicDTO1 studentCreateTopic(StudentCreateTopicRequest request) {
        Optional<User> userOptional = userService.getCurrentUser();
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            if (currentUser.getRole().equals("STUDENT")) {
                if (hasStudentSubmittedTopic(currentUser.getId())) {
                    throw new DataNotFoundException("Đơn đăng ký đề tài chỉ được gửi một lần, bạn đã gửi đơn đăng ký không thể gửi tiếp");

                }
                Topic topic = modelMapper.map(request, Topic.class);
                User teacher = userService.findTeacher(request.getTeacher());
                topic.setNameTopic(request.getNameTopic());
                topic.setTeacher(teacher);
                topic.setStudent(currentUser);
                topic.setSemester(request.getSemester());
                topic.setStatus("pending");
                topic.setDepartmentManagement(request.getDepartmentManagement());
                topic.setMenterInternshipFacility(request.getMenterInternshipFacility());
                topic.setNameInternshipFacility(request.getNameInternshipFacility());
                topic.setPhoneInstructorInternshipFacility(request.getPhoneInstructorInternshipFacility());
                Topic saveTopic = topicRepository.saveAndFlush(topic);
                return modelMapper.map(saveTopic, TopicDTO1.class);
            } else {
                throw new IllegalStateException("Người dùng đăng nhập không phải là sinh viên.");
            }
        } else {
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng đang đăng nhập");
        }
    }

    public boolean hasStudentSubmittedTopic(Integer userId) {
        return topicRepository.existsByStudentId(userId);
    }


    public TopicDTO updateTopic(UpdateTopicRequest request) {
        User teacher = userService.findTeacher(request.getTeacherId());
        User student = userService.findStudent(request.getStudentId());
        Optional<Topic> topicOptional = topicRepository.findById(request.getId());
        Topic topic = topicOptional.get();
        topic.setNameTopic(request.getNameTopic());
        topic.setStatus(request.getStatus());
        topic.setTeacher(teacher);
        topic.setStudent(student);
        topic.setSemester(request.getSemester());
        topic.setDepartmentManagement(request.getDepartmentManagement());
        topic.setMenterInternshipFacility(request.getMenterInternshipFacility());
        topic.setNameInternshipFacility(request.getNameInternshipFacility());
        topic.setPhoneInstructorInternshipFacility(request.getPhoneInstructorInternshipFacility());
        topic.setInstructor(request.getInstructor());
        topic.setReviewer(request.getReviewer());
        topic.setBoardMembers1(request.getBoardMembers1());
        topic.setBoardMembers2(request.getBoardMembers2());
        topic.setBoardMembers3(request.getBoardMembers3());
        topic.setCreateAt(new Timestamp(System.currentTimeMillis()));
        topic.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return modelMapper.map(topicRepository.saveAndFlush(topic), TopicDTO.class);
    }

    public List<ErrorDetail> deleteTopics(DeleteTopicRequest request) {
        log.info("deleteUser: {}", request.getIds());
        List<TopicDeleteDTO> users = usersRepository.findAllById(request.getIds()).stream()
                .map(u -> modelMapper.map(u, TopicDeleteDTO.class)).toList();
        List<ErrorDetail> errorDetails = new ArrayList<>();
        for (Integer requesId : request.getIds()) {
            boolean isExit = users.stream().anyMatch(u -> u.getId().equals(requesId));
            if (!isExit) {
                ErrorDetail errorDetail = new ErrorDetail();
                errorDetail.setId(requesId.toString());
                errorDetail.setMessage(Constants.ErrorMessageTopicValidation.NOT_FIND_TOPIC_BY_ID+ request.getIds());
                errorDetails.add(errorDetail);
            }
        }

        if (errorDetails.isEmpty()) {
            usersRepository.deleteAllById(request.getIds());
            return null;
        }
        return errorDetails;
    }

    public TopicDTO getTopicById(Integer id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (!topic.isPresent()) {
            throw new DataNotFoundException("Không tìm thấy đề tài có ID " + id);
        }
        return modelMapper.map(topic.get(), TopicDTO.class);
    }

    public Page<Topic> getTopicByParam(CustomTopicQuery.TopicFilterParam param, PageRequest request) {
        Specification<Topic> specification = CustomTopicQuery.getFilterTopic(param);
        return topicRepository.findAll(specification, request);
    }

    public boolean existsById(Integer topicId) {
        return topicRepository.existsById(topicId);
    }

    public Topic findById(Integer topicId) {
        return topicRepository.findById(topicId).orElseThrow(() -> new DataNotFoundException("Không tìm thấy topic với id là " + topicId));
    }




}
