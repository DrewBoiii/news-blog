package com.example.newsblog.service;

import com.example.newsblog.persistence.dto.article.ArticleCriteria;
import com.example.newsblog.persistence.dto.article.ArticleSaveDto;
import com.example.newsblog.persistence.dto.article.ArticleUpdateDto;
import com.example.newsblog.persistence.model.Article;

import java.util.List;

public interface ArticleService {

    Long save(ArticleSaveDto articleSaveDto);
    void update(ArticleUpdateDto articleUpdateDto);
    void deleteById(Long id);
    Article getById(Long id);
    List<Article> getAll();
    List<Article> getAllByCriteria(ArticleCriteria criteria);

}
