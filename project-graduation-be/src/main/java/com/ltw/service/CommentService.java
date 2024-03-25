package com.ltw.service;

import com.ltw.dto.entity.comment.CommentDTO;
import com.ltw.dto.request.comment.CreateCommentRequest;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.Comment;
import com.ltw.model.Topic;
import com.ltw.model.User;
import com.ltw.repository.comment.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service("CommentService")
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper modelMapper;
    private final TopicService topicService;
    private final  UserService userService;

    public CommentDTO createComment(CreateCommentRequest request) {

        if(!topicService.existsById(request.getTopicId())){
            throw new DataNotFoundException("Không tìm thấy topic với id là: " +  request.getTopicId());
        }
        Topic topic = topicService.findById(request.getTopicId());

        Optional<User> userOptional = userService.getCurrentUser();
        if(userOptional.isEmpty()){
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng comment");
        }
        User user = userOptional.get();

        //khi nao viet đến đăng nhập thì sẽ thêm lấy thông tin từ login ra để thêm username người đăng nhập vào thành người tạo comment
        try {
            Comment comment = modelMapper.map(request, Comment.class);
            comment.setMessage(request.getMessage());
            comment.setCreateBy(user.getUsername());
            comment.setTopic(topic);
            comment.setCreateAt(new Timestamp(System.currentTimeMillis()));
            return modelMapper.map(commentsRepository.saveAndFlush(comment), CommentDTO.class);
        } catch (Exception ex) {
            throw new DataNotFoundException("Quá trình tạo nhận xét thành công " + ex);
        }
    }


}
