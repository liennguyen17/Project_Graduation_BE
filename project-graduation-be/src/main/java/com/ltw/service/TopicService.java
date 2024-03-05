package com.ltw.service;

import com.ltw.constant.Constants;
import com.ltw.dto.entity.topic.TopicDTO;
import com.ltw.dto.entity.topic.TopicDeleteDTO;
import com.ltw.dto.entity.users.UserDeleteDTO;
import com.ltw.dto.request.topic.CreateTopicRequest;
import com.ltw.dto.request.topic.DeleteTopicRequest;
import com.ltw.dto.request.topic.UpdateTopicRequest;
import com.ltw.dto.request.user.DeleteUsersRequest;
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

    public TopicDTO createTopic(CreateTopicRequest request) {
//        try{
        Optional<User> optionalUserStudent = usersRepository.findById(request.getStudentId());
        Optional<User> optionalUserTeacher = usersRepository.findById(request.getTeacherId());
        Topic topic = modelMapper.map(request, Topic.class);
        topic.setNameTopic(request.getNameTopic());
        topic.setStatus(request.getStatus());
        topic.setInstructor(request.getInstructor());
        topic.setReviewer(request.getReviewer());
        topic.setBoardMembers1(request.getBoardMembers1());
        topic.setBoardMembers2(request.getBoardMembers2());
        topic.setBoardMembers3(request.getBoardMembers3());
        topic.setCreateAt(new Timestamp(System.currentTimeMillis()));
        topic.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return modelMapper.map(topicRepository.saveAndFlush(topic), TopicDTO.class);
//            }catch(Exception ex){
//            throw new DataNotFoundException("Qu")
//        }
    }

    public TopicDTO updateTopic(UpdateTopicRequest request) {
        Optional<Topic> topicOptional = topicRepository.findById(request.getId());
        Topic topic = topicOptional.get();
        topic.setNameTopic(request.getNameTopic());
        topic.setStatus(request.getStatus());
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
                errorDetail.setMessage(Constants.ErrorMessageUserValidation.NOT_FIND_USER_BY_ID + request.getIds());
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
}
