package com.ltw.repository.topic;

import com.ltw.model.Notification;
import com.ltw.model.Topic;
import com.ltw.model.User;
import com.ltw.repository.notification.CustomNotificationQuery;
import com.ltw.utils.CriteriaBuilderUtils;
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
public class CustomTopicQuery {

    public static Specification<Topic> getFilterTopic(CustomTopicQuery.TopicFilterParam param) {
        return ((((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!Strings.isEmpty(param.getKeywords())) {
                predicates.add(
                        CriteriaBuilderUtils.createPredicateForSearchInsensitve(root, criteriaBuilder, param.getKeywords(),
                                "nameTopic", "status","semester","departmentManagement")
                );
            }

//            if(param.getStudent() != null && !StringUtils.isEmpty(param.getStudent().getName())){
//                predicates.add(criteriaBuilder.like(
//                        criteriaBuilder.lower(root.get("student").get("name")), "%" + param.getStudent().getName().toLowerCase(Locale.ROOT) + "%"
//                ));
//            }

            if (param.getStudent() != null && !StringUtils.isEmpty(param.getStudent().getName())) {
                String trimmedName = param.getStudent().getName().trim();
                if (!StringUtils.isEmpty(trimmedName)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("student").get("name")),
                            "%" + trimmedName.toLowerCase(Locale.ROOT) + "%"
                    ));
                }
            }

            if (param.getStudent() != null && !StringUtils.isEmpty(param.getStudent().getUserCode())) {
                String trimmedName = param.getStudent().getUserCode().trim();
                if (!StringUtils.isEmpty(trimmedName)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("student").get("userCode")),
                            "%" + trimmedName.toLowerCase(Locale.ROOT) + "%"
                    ));
                }
            }

            if (param.getTeacher() != null && !StringUtils.isEmpty(param.getTeacher().getName())) {
                String trimmedName = param.getTeacher().getName().trim();
                if (!StringUtils.isEmpty(trimmedName)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("teacher").get("name")),
                            "%" + trimmedName.toLowerCase(Locale.ROOT) + "%"
                    ));
                }
            }

            if (param.getTeacher() != null && !StringUtils.isEmpty(param.getTeacher().getUserCode())) {
                String trimmedName = param.getTeacher().getUserCode().trim();
                if (!StringUtils.isEmpty(trimmedName)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("teacher").get("userCode")),
                            "%" + trimmedName.toLowerCase(Locale.ROOT) + "%"
                    ));
                }
            }

            if (StringUtils.hasText(param.getNameTopic())) {
                String trimmed = param.getNameTopic().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("nameTopic"), "%" + trimmed + "%"));
                }
            }


            if (StringUtils.hasText(param.getDepartmentManagement())) {
                String trimmed = param.getDepartmentManagement().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("departmentManagement"), trimmed));
                }
            }

            if (StringUtils.hasText(param.getSemester())) {
                String trimmed = param.getSemester().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("semester"), trimmed));
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

    @Data
    @NoArgsConstructor
    public static class TopicFilterParam {
        private String keywords;
        private String sortType;
        private String sortField;
        private String semester;
        private String nameTopic;
        private String status;
        private String departmentManagement;
        private User student;
        private User teacher;
    }
}
