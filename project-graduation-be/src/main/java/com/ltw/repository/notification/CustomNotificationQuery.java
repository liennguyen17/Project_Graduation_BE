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
import org.springframework.util.StringUtils;

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

            if (StringUtils.hasText(param.getTitle())) {
                String trimmed = param.getTitle().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + trimmed + "%"));
                }
            }

            if (StringUtils.hasText(param.getDescription())) {
                String trimmed = param.getDescription().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("description"), "%" + trimmed + "%"));
                }
            }

            query.orderBy(criteriaBuilder.desc(root.get("id")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        })));
    }

    @Data
    @NoArgsConstructor
    public static class NotificationFilterParam {
        private String keywords;
        private String sortType;
        private String sortField;
        private String title;
        private String description;
    }
}
