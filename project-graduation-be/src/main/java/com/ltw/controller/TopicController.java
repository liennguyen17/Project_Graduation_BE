package com.ltw.controller;

import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.entity.notification.NotificationDTO;
import com.ltw.dto.entity.topic.TopicDTO;
import com.ltw.dto.entity.topic.TopicDTO1;
import com.ltw.dto.request.topic.*;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseListResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.model.Topic;
import com.ltw.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;
    private final ModelMapper modelMapper;

    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGE','TEACHER','STUDENT')")
    public BaseItemResponse<TopicDTO> createTopic(@Valid @RequestBody CreateTopicRequest request) {
        return BaseResponse.successData(topicService.createTopic(request));
    }

    @PutMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGE','TEACHER')")
    public BaseItemResponse<TopicDTO> updateTopic(@Valid @RequestBody UpdateTopicRequest request) {
        return BaseResponse.successData(topicService.updateTopic(request));
    }

    @DeleteMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGE','TEACHER')")
    public BaseResponse deleteNotification(@Valid @RequestBody DeleteTopicRequest request) {
        List<ErrorDetail> errorDetailList = topicService.deleteTopics(request);
        if (errorDetailList == null) {
            return BaseResponse.successData("Xóa đề tài thành công");
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

    @PostMapping("/student")
    public  BaseItemResponse<TopicDTO1> studentCreateTopic(@Valid @RequestBody StudentCreateTopicRequest request){
        return BaseResponse.successData(topicService.studentCreateTopic(request));
    }


    @GetMapping(value = "/generate-pdf" ,produces = MediaType.APPLICATION_PDF_VALUE)
//    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<InputStreamResource> generatePdf(){
        ByteArrayInputStream bis = topicService.generatePdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=don_xin_xac_nhan.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
