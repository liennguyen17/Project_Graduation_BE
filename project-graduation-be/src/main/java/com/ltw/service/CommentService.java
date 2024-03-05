package com.ltw.service;

import com.ltw.dto.entity.comment.CommentDTO;
import com.ltw.dto.request.comment.CreateCommentRequest;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.Comment;
import com.ltw.repository.comment.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service("CommentService")
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper modelMapper;

    public CommentDTO createComment(CreateCommentRequest request) {

        //khi nao viet đến đăng nhập thì sẽ thêm lấy thông tin từ login ra để thêm username người đăng nhập vào thành người tạo comment
        try {
            Comment comment = modelMapper.map(request, Comment.class);
            comment.setMessage(request.getMessage());
            comment.setCreateBy(request.getCreateBy());
            comment.setCreateAt(new Timestamp(System.currentTimeMillis()));
            return modelMapper.map(commentsRepository.saveAndFlush(comment), CommentDTO.class);
        } catch (Exception ex) {
            throw new DataNotFoundException("Quá trình tạo nhận xét thành công " + ex);
        }
    }
}
