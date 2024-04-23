package com.ltw.controller;

import com.ltw.dto.entity.comment.CommentDTO;
import com.ltw.dto.entity.comment.CommentUserDTO;
import com.ltw.dto.request.comment.CreateCommentRequest;
import com.ltw.dto.request.comment.ListCommentRequest;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseListResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public BaseItemResponse<CommentDTO> createComment(@Valid @RequestBody CreateCommentRequest request) {
        return BaseResponse.successData(commentService.createComment(request));
    }

    @GetMapping("/all")
    public BaseListResponse<CommentDTO> getAllComment(){
        List<CommentDTO> response = commentService.getAllComment();
        return BaseResponse.successListData(response, response.size());
    }

    @PostMapping("/topic")
    @PreAuthorize("hasAnyAuthority('TEACHER','STUDENT')")
    public BaseListResponse<CommentUserDTO> listComment(@Valid @RequestBody ListCommentRequest request){
        return BaseResponse.successListData(commentService.listComment(request.getTopicId()), commentService.listComment(request.getTopicId()).size());
    }
}
