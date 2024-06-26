package com.ltw.repository.comment;

import com.ltw.dto.entity.comment.CommentUserDTO;
import com.ltw.model.Comment;
import com.ltw.model.Notification;
import com.ltw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {
    List<Comment> findByTopicId(Integer topicId);
    List<Comment> findByTopic_Teacher(User user);
    List<Comment> findByTopic_Student(User user);
}
