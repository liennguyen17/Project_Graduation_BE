package com.ltw.repository.topic;

import com.ltw.model.Notification;
import com.ltw.model.Topic;
import com.ltw.repository.notification.CustomNotificationQuery;
import com.ltw.utils.CriteriaBuilderUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CustomTopicQuery {
    public static Specification<Topic> getFilterTopic(CustomTopicQuery.TopicFilterParam param) {
        return ((((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!Strings.isEmpty(param.getKeywords())) {
                predicates.add(
                        CriteriaBuilderUtils.createPredicateForSearchInsensitve(root, criteriaBuilder, param.getKeywords(),
                                "nameTopic", "status")
                );
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        })));
    }

    @Data
    @NoArgsConstructor
    public static class TopicFilterParam {
        private String keywords;
        private String sortType;
        private String sortField;
    }
}
