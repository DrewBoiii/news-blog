package com.example.newsblog.persistence.dto.article;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ArticleCriteriaDto {

    public static final String DEFAULT_TITLE_CRITERIA = "";

    public static final String DEFAULT_USERNAME_CRITERIA = "";

    public static final String DEFAULT_SORT_CRITERIA = "createdAt";

    public static final String DEFAULT_ORDER_CRITERIA = "desc";

    @Size(max = 255)
    private String title = DEFAULT_TITLE_CRITERIA;

    @Size(max = 255)
    private String username = DEFAULT_USERNAME_CRITERIA;

    @Size(max = 255)
    private String sort = DEFAULT_SORT_CRITERIA;

    @Size(max = 4)
    private String order = DEFAULT_ORDER_CRITERIA;

}
