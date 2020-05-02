package com.example.newsblog.service;

import com.example.newsblog.persistence.dto.article.ArticleCriteria;
import com.example.newsblog.persistence.dto.article.ArticleSaveDto;
import com.example.newsblog.persistence.dto.article.ArticleUpdateDto;
import com.example.newsblog.persistence.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

    Long save(ArticleSaveDto articleSaveDto);
    void update(ArticleUpdateDto articleUpdateDto);
    void deleteById(Long id);
    Article getById(Long id);
    Page<Article> getAll(Pageable pageable);
    Page<Article> getAll(ArticleCriteria criteria, Pageable pageable);

}
