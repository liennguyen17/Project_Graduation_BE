package com.ltw.controller;

import com.ltw.dto.entity.comment.CommentDTO;
import com.ltw.dto.request.comment.CreateCommentRequest;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
