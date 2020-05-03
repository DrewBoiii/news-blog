package com.example.newsblog.persistence.dto.article;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ArticleCriteriaDto {

    public static final String DEFAULT_SORT_CRITERIA = "createdAt";

    public static final String DEFAULT_ORDER_CRITERIA = "desc";

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private String sort = DEFAULT_SORT_CRITERIA;

    @Size(max = 4)
    private String order = DEFAULT_ORDER_CRITERIA;

}
