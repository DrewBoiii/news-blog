package com.example.newsblog.specification;

import com.example.newsblog.persistence.model.Article;
import com.example.newsblog.util.HtmlSanitizerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ArticleSpecification {

    public static Specification<Article> getArticlesByTitle(String title) {
        return StringUtils.isNotBlank(title) ? new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("title")),
                        "%" + HtmlSanitizerUtil.sanitize(title.toUpperCase().trim()) + "%");
            }
        } : null;
    }

    public static Specification<Article> getArticleByUsername(String username) {
        return StringUtils.isNotBlank(username) ? new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(
                        criteriaBuilder.upper(
                                root.join("user", JoinType.LEFT).getParent().get("user").get("username")
                        ),
                        HtmlSanitizerUtil.sanitize(username.toUpperCase().trim())
                );
            }
        } : null;
    }

}
