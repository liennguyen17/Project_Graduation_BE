package com.ltw.repository.users;

import com.ltw.constant.Constants;
import com.ltw.model.User;
import com.ltw.utils.CriteriaBuilderUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomUserQuery {
    private CustomUserQuery(){}
    @Data
    @NoArgsConstructor
    public static class UserFilterParam{
        private  String keywords;
        private String sortType;
        private String sortField;
        private String email;
        private String phone;
        private String role;
        private String userCode;
        private String className;
        private String name;
        private String username;
        private String subject;
        private String address;
        private Date startDate;
        private Date endDate;
    }

    public static Specification<User> getFilterUser(UserFilterParam param){
        return ((((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!Strings.isEmpty(param.getKeywords())){
                predicates.add(
                        CriteriaBuilderUtils.createPredicateForSearchInsensitve(root, criteriaBuilder, param.getKeywords(),
                                "name", "username","address","subject","role","userCode","className" )
                );

            }



            if (StringUtils.hasText(param.getAddress())) {
                String trimmedAddress = param.getAddress().trim();
                if (!trimmedAddress.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("address"), "%" + trimmedAddress + "%"));
                }
            }

            if (StringUtils.hasText(param.getSubject())) {
                String trimmed = param.getSubject().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("subject"), "%" + trimmed + "%"));
                }
            }


            if (StringUtils.hasText(param.getEmail())) {
                String trimmed = param.getEmail().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("email"), trimmed));
                }
            }

            if (StringUtils.hasText(param.getPhone())) {
                String trimmed = param.getPhone().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("phone"), trimmed));
                }
            }
            if (StringUtils.hasText(param.getRole())) {
                String trimmed = param.getRole().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("role"), trimmed));
                }
            }
            if (StringUtils.hasText(param.getUserCode())) {
                String trimmed = param.getUserCode().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("userCode"), trimmed));
                }
            }

            if (StringUtils.hasText(param.getClassName())) {
                String trimmed = param.getClassName().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("className"), "%" + trimmed + "%"));
                }
            }

            if (StringUtils.hasText(param.getName())) {
                String trimmed = param.getName().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + trimmed + "%"));
                }
            }

            if (StringUtils.hasText(param.getUsername())) {
                String trimmed = param.getUsername().trim();
                if (!trimmed.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("username"), "%" + trimmed + "%"));
                }
            }
//            if (param.startDate != null && param.endDate != null) {
////                Timestamp startDateValue = new Timestamp(param.startDate);
////                Timestamp endDateValue = new Timestamp(param.endDate);
//                predicates.add(criteriaBuilder.between(root.get("updateAt"), param.startDate, param.endDate));
//            }
            if(param.sortField != null && !param.sortField.equals("")){
                if(param.sortType.equals(Constants.SortType.DESC) || param.sortType.equals("")){
                    query.orderBy(criteriaBuilder.desc(root.get(param.sortField)));
                }
                if(param.sortType.equals(Constants.SortType.ASC)){
                    query.orderBy(criteriaBuilder.asc(root.get(param.sortField)));
                }
            }else {
                query.orderBy(criteriaBuilder.desc(root.get("id")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        })));
    }

}
