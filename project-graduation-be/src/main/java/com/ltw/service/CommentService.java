package com.ltw.service;

import com.ltw.dto.entity.comment.CommentDTO;
import com.ltw.dto.entity.comment.CommentUserDTO;
import com.ltw.dto.request.comment.CreateCommentRequest;
import com.ltw.dto.request.comment.ListTopicCommentRequest;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.Comment;
import com.ltw.model.Topic;
import com.ltw.model.User;
import com.ltw.repository.comment.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service("CommentService")
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper modelMapper;
    private final TopicService topicService;
    private final  UserService userService;

    public CommentDTO createComment(CreateCommentRequest request) {

        if(!topicService.existsById(request.getTopic())){
            throw new DataNotFoundException("Không tìm thấy topic với id là: " +  request.getTopic());
        }
        Topic topic = topicService.findById(request.getTopic());

        Optional<User> userOptional = userService.getCurrentUser();
        if(userOptional.isEmpty()){
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng comment");
        }
        User user = userOptional.get();

        try {
            Comment comment = modelMapper.map(request, Comment.class);
            comment.setMessage(request.getMessage());
            comment.setCreateBy(user.getUsername());
            comment.setTopic(topic);
            comment.setDescriptionFile(request.getDescriptionFile());
            comment.setCreateAt(new Timestamp(System.currentTimeMillis()));
            return modelMapper.map(commentsRepository.saveAndFlush(comment), CommentDTO.class);
        } catch (Exception ex) {
            throw new DataNotFoundException("Quá trình tạo nhận xét thành công " + ex);
        }
    }

    public List<CommentDTO> getAllComment() {
        return commentsRepository.findAll().stream().map(
                comment -> modelMapper.map(comment, CommentDTO.class)
        ).toList();
    }

    public List<CommentUserDTO> listComment(Integer id) {
        List<Comment> comments = commentsRepository.findByTopicId(id);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentUserDTO.class)).collect(Collectors.toList());

    }

    public List<CommentDTO> getCommentForTeacher() {
        Optional<User> currentUserOptional = userService.getCurrentUser();
        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            if (currentUser.getRole().equals("TEACHER")) {
                List<Comment> comments = commentsRepository.findByTopic_Teacher(currentUser);
                List<CommentDTO> commentDTOS = new ArrayList<>();
                Set<Integer> displayedTopicIds = new HashSet<>();
                for (Comment comment : comments) {
                    Integer topicId = comment.getTopic().getId();
                    //kt topic id đã được hiện thị chưa
                    if (!displayedTopicIds.contains(topicId)) {
                        //them topic id dai dien vao danh sach
                        displayedTopicIds.add(topicId);
                        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                        commentDTOS.add(commentDTO);
                    }
                }
                return commentDTOS;
            } else {
                throw new DataNotFoundException("Người dùng không phải là giáo viên.");
            }
        } else {
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng đăng nhập.");
        }
    }

    public List<CommentDTO> getCommentForStudent() {
        Optional<User> currentUserOptional = userService.getCurrentUser();
        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            if (currentUser.getRole().equals("STUDENT")) {
                List<Comment> comments = commentsRepository.findByTopic_Student(currentUser);
                List<CommentDTO> commentDTOS = new ArrayList<>();
                Set<Integer> displayedTopicIds = new HashSet<>();
                for (Comment comment : comments) {
                    CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                    commentDTOS.add(commentDTO);
                }
                return commentDTOS;
            } else {
                throw new DataNotFoundException("Người dùng không phải là sinh viên.");
            }
        } else {
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng đăng nhập.");
        }
    }

//    public List<CommentDTO> getCommentByTopicId(ListTopicCommentRequest request){
//        List<Comment> comments = commentsRepository.si
//    }


}
