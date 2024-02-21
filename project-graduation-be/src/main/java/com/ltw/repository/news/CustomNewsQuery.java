package com.ltw.repository.news;

import com.ltw.constant.Constants;
import com.ltw.model.News;
import com.ltw.utils.CriteriaBuilderUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class CustomNewsQuery {
    private CustomNewsQuery(){}

    @Data
    @NoArgsConstructor
    public static class NewsFilterParam{
        private String keywords;
        private Year year;
        private String sortField;
        private String sortType;
        private Long startDate;
        private Long endDate;
    }

    public static Specification<News> getFilterNew(NewsFilterParam param){
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!Strings.isEmpty(param.getKeywords())){
                predicates.add(
                        CriteriaBuilderUtils.createPredicateForSearchInsensitve(root, criteriaBuilder, param.getKeywords(),
                                "title","description","subject")
                );
            }
            if(param.getYear() != null){
                predicates.add(criteriaBuilder.equal(root.get("year"), param.getYear()));
            }
            if (param.startDate != null && param.endDate != null) {
                Timestamp startDateValue = new Timestamp(param.startDate);
                Timestamp endDateValue = new Timestamp(param.endDate);
                predicates.add(criteriaBuilder.between(root.get("createdAt"), startDateValue, endDateValue));
            }
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
        }));

    }
}
