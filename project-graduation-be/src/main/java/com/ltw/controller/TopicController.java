package com.ltw.controller;

import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.entity.notification.NotificationDTO;
import com.ltw.dto.entity.topic.TopicDTO;
import com.ltw.dto.request.topic.CreateTopicRequest;
import com.ltw.dto.request.topic.DeleteTopicRequest;
import com.ltw.dto.request.topic.GetTopicRequest;
import com.ltw.dto.request.topic.UpdateTopicRequest;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseListResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.model.Topic;
import com.ltw.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;
    private final ModelMapper modelMapper;

    @PostMapping
    public BaseItemResponse<TopicDTO> createTopic(@Valid @RequestBody CreateTopicRequest request) {
        return BaseResponse.successData(topicService.createTopic(request));
    }

    @PutMapping
    public BaseItemResponse<TopicDTO> updateTopic(@Valid @RequestBody UpdateTopicRequest request) {
        return BaseResponse.successData(topicService.updateTopic(request));
    }

    @DeleteMapping
    public BaseResponse deleteNotification(@Valid @RequestBody DeleteTopicRequest request) {
        List<ErrorDetail> errorDetailList = topicService.deleteTopics(request);
        if (errorDetailList == null) {
            return BaseResponse.successData("Xóa thông báo thành công");
        }
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION, ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_VALIDATION), errorDetailList);
    }

    @GetMapping("{id}")
    public BaseItemResponse<TopicDTO> getByIdTopic(@PathVariable("id") Integer id) {
        return BaseResponse.successData(topicService.getTopicById(id));
    }

    @PostMapping("/filter")
    public BaseListResponse<TopicDTO> filterTopic(@Valid @RequestBody GetTopicRequest request) {
        Page<Topic> topicPage = topicService.getTopicByParam(request, PageRequest.of(request.getStart(), request.getLimit()));
        return BaseResponse.successListData(topicPage.getContent().stream().map(e -> modelMapper.map(e, TopicDTO.class)).collect(Collectors.toList()), (int) topicPage.getTotalElements());
    }
}
