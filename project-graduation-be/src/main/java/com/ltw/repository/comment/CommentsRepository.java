package com.ltw.repository.comment;

import com.ltw.model.Comment;
import com.ltw.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {
}
