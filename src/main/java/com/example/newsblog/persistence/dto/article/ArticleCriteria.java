package com.example.newsblog.persistence.dto.article;

import lombok.Data;

@Data
public class ArticleCriteria {

    public static final String DEFAULT_SORT_CRITERIA = "createdAt";

    private String title;

    private String username;

    private String sort = DEFAULT_SORT_CRITERIA;



}
