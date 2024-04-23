package com.ltw.repository.topicChangeName;

import com.ltw.model.TopicChangeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicChangeNameRepository extends JpaRepository<TopicChangeName, Integer>, JpaSpecificationExecutor<TopicChangeName> {
}
