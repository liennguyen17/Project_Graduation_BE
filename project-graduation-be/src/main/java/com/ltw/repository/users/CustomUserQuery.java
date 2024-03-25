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
            if(StringUtils.hasText(param.getAddress())){
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + param.getAddress() + "%"));
            }
            if(StringUtils.hasText(param.subject)){
                predicates.add(criteriaBuilder.like(root.get("subject"), "%" + param.getSubject() + "%"));
            }
            if(StringUtils.hasText(param.getEmail())){
                predicates.add(criteriaBuilder.equal(root.get("email"), param.email));
            }
            if(StringUtils.hasText(param.getPhone())){
                predicates.add(criteriaBuilder.equal(root.get("phone"), param.phone));
            }
            if(StringUtils.hasText(param.getRole())){
                predicates.add(criteriaBuilder.equal(root.get("role"), param.role));
            }
            if(StringUtils.hasText(param.getUserCode())){
                predicates.add(criteriaBuilder.equal(root.get("userCode"), param.userCode));
            }
            if(StringUtils.hasText(param.getClassName())){
                predicates.add(criteriaBuilder.equal(root.get("className"), param.className));
            }
            if(StringUtils.hasText(param.getName())){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if(StringUtils.hasText(param.getUsername())){
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + param.getUsername() + "%"));
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
