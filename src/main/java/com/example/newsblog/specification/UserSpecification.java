package com.example.newsblog.specification;

import com.example.newsblog.persistence.model.User;
import com.example.newsblog.util.HtmlSanitizerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification {

    public static Specification<User> getUsersByUsername(String username) {
        return StringUtils.isNotBlank(username) ? new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("username")),
                        "%" + HtmlSanitizerUtil.sanitize(username.toUpperCase()) + "%");
            }
        } : null;
    }

    public static Specification<User> getUsersByFirstName(String firstName) {
        return StringUtils.isNotBlank(firstName) ? new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("firstName")),
                        "%" + HtmlSanitizerUtil.sanitize(firstName.toUpperCase()) + "%");
            }
        } : null;
    }

    public static Specification<User> getUsersByLastName(String lastName) {
        return StringUtils.isNotBlank(lastName) ? new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("lastName")),
                        "%" + HtmlSanitizerUtil.sanitize(lastName.toUpperCase()) + "%");
            }
        } : null;
    }

}
