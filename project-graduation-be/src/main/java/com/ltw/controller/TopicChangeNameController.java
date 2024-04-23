package com.ltw.controller;

import com.ltw.dto.entity.topicChangeName.TopicChangeNameDTO;
import com.ltw.dto.request.topicChangeName.CreateChangeTopicRequest;
import com.ltw.dto.request.topicChangeName.GetTopicChangeNameRequest;
import com.ltw.dto.request.topicChangeName.UpdateChangeNameTopicRequest;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseListResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.model.TopicChangeName;
import com.ltw.service.TopicChangeNameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/topic-change-name")
@RequiredArgsConstructor
public class TopicChangeNameController {
    private final TopicChangeNameService topicChangeNameService;
    private final ModelMapper modelMapper;

    @GetMapping
    public BaseItemResponse<TopicChangeNameDTO> filteropicChangeName(){
        return BaseResponse.successData(topicChangeNameService.filterTopicOfUser());
    }

    @GetMapping("/list-topic-student")
    public BaseListResponse<TopicChangeNameDTO> listTopicOfStudent(){
        return BaseResponse.successListData(topicChangeNameService.listTopicOfStudent(), topicChangeNameService.listTopicOfStudent().size());
    }

    @PostMapping
    public BaseItemResponse<TopicChangeNameDTO> createTopicChangeName(@Valid @RequestBody CreateChangeTopicRequest request){
        return BaseResponse.successData(topicChangeNameService.createChangeTopic(request));
    }

    @PutMapping
    public BaseItemResponse<TopicChangeNameDTO> updateTopicChangeName(@Valid @RequestBody UpdateChangeNameTopicRequest request){
        return BaseResponse.successData(topicChangeNameService.updateChangeNameTopic(request));
    }

    @PostMapping("/filter")
    public BaseListResponse<TopicChangeNameDTO> filterTopicChangeName(@Valid @RequestBody GetTopicChangeNameRequest request){
        Page<TopicChangeName> topicChangeNames = topicChangeNameService.getTopicChangeName(request,  PageRequest.of(request.getStart(), request.getLimit()));
        return BaseResponse.successListData(topicChangeNames.getContent().stream().map(e -> modelMapper.map(e, TopicChangeNameDTO.class)).collect(Collectors.toList()),(int) topicChangeNames.getTotalElements());
    }
}
