package com.ltw.repository.topic;

import com.ltw.model.Notification;
import com.ltw.model.Topic;
import com.ltw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer>, JpaSpecificationExecutor<Topic> {
    boolean existsByStudentId(Integer id);
    List<Topic> findByTeacher(User user);
    Topic findByStudent(User user);
    List<Topic> findBySemester(String semester);

    boolean existsByStudentIdAndSemester(Integer studentId, String semester);

    List<Topic> findTopicByStudentOrderByCreateAtDesc(User user);
}
