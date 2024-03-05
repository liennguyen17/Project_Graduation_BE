package com.ltw.repository.notification;

import com.ltw.model.Notification;
import com.ltw.model.User;
import com.ltw.repository.users.CustomUserQuery;
import com.ltw.utils.CriteriaBuilderUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CustomNotificationQuery {
    public static Specification<Notification> getFilterNotification(CustomNotificationQuery.NotificationFilterParam param) {
        return ((((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!Strings.isEmpty(param.getKeywords())) {
                predicates.add(
                        CriteriaBuilderUtils.createPredicateForSearchInsensitve(root, criteriaBuilder, param.getKeywords(),
                                "title", "description", "isRead")
                );
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        })));
    }

    @Data
    @NoArgsConstructor
    public static class NotificationFilterParam {
        private String keywords;
        private String sortType;
        private String sortField;
    }
}
