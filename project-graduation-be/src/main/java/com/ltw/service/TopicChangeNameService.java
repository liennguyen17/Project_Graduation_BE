package com.ltw.service;

import com.ltw.dto.entity.topicChangeName.TopicChangeNameDTO;
import com.ltw.dto.request.topicChangeName.CreateChangeTopicRequest;
import com.ltw.dto.request.topicChangeName.UpdateChangeNameTopicRequest;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.Topic;
import com.ltw.model.TopicChangeName;
import com.ltw.model.User;
import com.ltw.repository.topic.TopicRepository;
import com.ltw.repository.topicChangeName.CustomTopicChangeNameQuery;
import com.ltw.repository.topicChangeName.TopicChangeNameRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("TopicChangeNameService")
@RequiredArgsConstructor
public class TopicChangeNameService {
    private final TopicChangeNameRepository topicChangeNameRepository;
    private final ModelMapper modelMapper;
    private final TopicService topicService;
    private final UserService userService;
    private final TopicRepository topicRepository;

    public TopicChangeNameDTO createChangeTopic(CreateChangeTopicRequest request){
        Topic userTopic = findTopicFromStudentLogin();

        TopicChangeName topicChangeName = modelMapper.map(request, TopicChangeName.class);
        topicChangeName.setNewNameTopic(request.getNewNameTopic());
        topicChangeName.setReason(request.getReason());
        topicChangeName.setStatus(request.getStatus());
        topicChangeName.setTopic(userTopic);
        topicChangeName.setOldNameTopic(userTopic.getNameTopic());
        topicChangeName.setCreateAt(new Timestamp(System.currentTimeMillis()));

        return modelMapper.map(topicChangeNameRepository.saveAndFlush(topicChangeName), TopicChangeNameDTO.class);
    }

    public  TopicChangeNameDTO updateChangeNameTopic(UpdateChangeNameTopicRequest request){

        Topic topic = topicService.findById(request.getTopic());

        Optional<TopicChangeName> topicChangeNameOptional = topicChangeNameRepository.findById(request.getId());
        if(!topicChangeNameOptional.isPresent()){
            throw new DataNotFoundException("Không tìm thấy đề tài thực hiện việc đổi tên với id là: " + request.getId());

        }

        TopicChangeName topicChangeName = topicChangeNameOptional.get();
        topicChangeName.setStatus(request.getStatus());
        topicChangeName.setNote(request.getNote());

        if("Đơn đổi đề tài đã được chấp nhận".equals(request.getStatus())){
            topic.setNameTopic(topicChangeName.getNewNameTopic());
            topicRepository.save(topic);
        }

        return modelMapper.map(topicChangeNameRepository.saveAndFlush(topicChangeName), TopicChangeNameDTO.class);

    }

    public Page<TopicChangeName> getTopicChangeName(CustomTopicChangeNameQuery.TopicFilterParam param, PageRequest request){
        Specification<TopicChangeName> specification = CustomTopicChangeNameQuery.getFilterTopicChangeName(param);
        return topicChangeNameRepository.findAll(specification, request);
    }

    public TopicChangeNameDTO filterTopicOfUser(){
        Topic userTopic = findTopicFromStudentLogin();
        if (userTopic == null) {
            throw new DataNotFoundException("Không tìm thấy đề tài của người dùng hiện tại.");
        }

        List<TopicChangeName> topicChangeNames = userTopic.getTopicChangeNames();
        if(topicChangeNames.isEmpty()){
            throw new DataNotFoundException("Không có thông tin về thay đổi đề tài");
        }

        int lastIndex = topicChangeNames.size() -1;
        TopicChangeName topicChangeName = topicChangeNames.get(lastIndex);
        TopicChangeNameDTO topicChangeNameDTO = modelMapper.map(topicChangeName, TopicChangeNameDTO.class);
        return topicChangeNameDTO;
    }

    public List<TopicChangeNameDTO> listTopicOfStudent(){
        Topic userTopic = findTopicFromStudentLogin();
        if (userTopic == null) {
            throw new DataNotFoundException("Không tìm thấy đề tài của người dùng hiện tại.");
        }

        List<TopicChangeName> topicChangeNames = userTopic.getTopicChangeNames();
        if(topicChangeNames.isEmpty()){
            throw new DataNotFoundException("Không có thông tin về thay đổi đề tài");
        }

        List<TopicChangeNameDTO> topicChangeNameDTOS = new ArrayList<>();
        for (TopicChangeName e : topicChangeNames){
            TopicChangeNameDTO topicChangeNameDTO = modelMapper.map(e, TopicChangeNameDTO.class);
            topicChangeNameDTOS.add(topicChangeNameDTO);
        }

        return topicChangeNameDTOS;
    }

    public Topic findTopicFromStudentLogin(){
        Optional<User> userOptional = userService.getCurrentUser();
        if(userOptional.isEmpty()){
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng hiện tại vui lòng đăng nhập lại.");
        }

        User currentUser = userOptional.get();
        Topic userTopic = topicRepository.findByStudent(currentUser);
        return userTopic;
    }


}
