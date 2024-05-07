package com.ltw.repository.topicChangeName;

import com.ltw.model.Topic;
import com.ltw.model.TopicChangeName;
import com.ltw.model.User;
import com.ltw.utils.CriteriaBuilderUtils;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@NoArgsConstructor
public class CustomTopicChangeNameQuery {
    @Data
    @NoArgsConstructor
    public static class TopicFilterParam {
        private String keywords;
        private User student;
        private String newNameTopic;
        private String status;
        private String semester;
        private Topic topic;
    }

    public static Specification<TopicChangeName> getFilterTopicChangeName(CustomTopicChangeNameQuery.TopicFilterParam param) {
        return ((((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!Strings.isEmpty(param.getKeywords())) {
                predicates.add(
                        CriteriaBuilderUtils.createPredicateForSearchInsensitve(root, criteriaBuilder, param.getKeywords(),
                                "newNameTopic", "status", "note", "reason","oldNameTopic")
                );
            }

            if (param.getTopic() != null) {
                Join<TopicChangeName, Topic> topicJoin = root.join("topic", JoinType.INNER);
                Topic topic = param.getTopic();
                String trimmed = topic.getSemester().trim();
                if (!StringUtils.isEmpty(trimmed)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(topicJoin.get("semester")),
                            "%" + trimmed.toLowerCase(Locale.ROOT) + "%"
                    ));
                }
                if (topic.getStudent() != null) {
                    User student = topic.getStudent();
                    String trimmed1 = student.getName().trim();
                    String trimmed2 = student.getUserCode().trim();
                    if (!StringUtils.isEmpty(trimmed1)) {
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(topicJoin.get("student").get("name")),
                                "%" + trimmed1.toLowerCase(Locale.ROOT) + "%"
                        ));
                    }
                    if (!StringUtils.isEmpty(trimmed2)) {
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(topicJoin.get("student").get("userCode")),
                                "%" + trimmed2.toLowerCase(Locale.ROOT) + "%"
                        ));
                    }
                }
            }
//            if (param.getStudent() != null && !StringUtils.isEmpty(param.getTopic().getSemester())){
//                String trimmed = param.getTopic().getSemester().trim();
//                if (!StringUtils.isEmpty(trimmed)) {
//                    predicates.add(criteriaBuilder.like(
//                            criteriaBuilder.lower(root.get("topic").get("semester")),
//                            "%" + trimmed.toLowerCase(Locale.ROOT) + "%"
//                    ));
//                }
//            }
//
//            if (param.getStudent() != null && !StringUtils.isEmpty(param.getTopic().getStudent().getName())) {
//                String trimmed = param.getTopic().getStudent().getName().trim();
//                if (!StringUtils.isEmpty(trimmed)) {
//                    predicates.add(criteriaBuilder.like(
//                            criteriaBuilder.lower(root.get("topic").get("student").get("name")),
//                            "%" + trimmed.toLowerCase(Locale.ROOT) + "%"
//                    ));
//                }
//            }
//
//            if (param.getStudent() != null && !StringUtils.isEmpty(param.getTopic().getStudent().getUserCode())) {
//                String trimmed = param.getTopic().getStudent().getUserCode().trim();
//                if (!StringUtils.isEmpty(trimmed)) {
//                    predicates.add(criteriaBuilder.like(
//                            criteriaBuilder.lower(root.get("topic").get("student").get("userCode")),
//                            "%" + trimmed.toLowerCase(Locale.ROOT) + "%"
//                    ));
//                }
//            }


            if (StringUtils.hasText(param.getNewNameTopic())) {
                String trimmed = param.getNewNameTopic().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("newNameTopic"), "%" + trimmed + "%"));
                }
            }

            if (StringUtils.hasText(param.getStatus())) {
                String trimmed = param.getStatus().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), trimmed));
                }
            }

            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        })));
    }
}
