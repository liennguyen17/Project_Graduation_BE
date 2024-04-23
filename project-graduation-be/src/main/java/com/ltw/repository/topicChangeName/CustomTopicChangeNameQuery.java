package com.ltw.repository.topicChangeName;

import com.ltw.model.TopicChangeName;
import com.ltw.model.User;
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
public class CustomTopicChangeNameQuery {
    @Data
    @NoArgsConstructor
    public static class TopicFilterParam {
        private String keywords;
        private User student;
        private String newNameTopic;
        private String status;
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

            if (param.getStudent() != null && !StringUtils.isEmpty(param.getStudent().getName())) {
                String trimmedName = param.getStudent().getName().trim();
                if (!StringUtils.isEmpty(trimmedName)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("topic").get("student").get("name")),
                            "%" + trimmedName.toLowerCase(Locale.ROOT) + "%"
                    ));
                }
            }

            if (param.getStudent() != null && !StringUtils.isEmpty(param.getStudent().getUserCode())) {
                String trimmedName = param.getStudent().getUserCode().trim();
                if (!StringUtils.isEmpty(trimmedName)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("topic").get("student").get("userCode")),
                            "%" + trimmedName.toLowerCase(Locale.ROOT) + "%"
                    ));
                }
            }

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
