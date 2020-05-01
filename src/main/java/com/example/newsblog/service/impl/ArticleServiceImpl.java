package com.example.newsblog.service.impl;

import com.example.newsblog.persistence.dto.article.ArticleCriteria;
import com.example.newsblog.specification.ArticleSpecification;
import com.example.newsblog.persistence.dao.ArticleRepository;
import com.example.newsblog.persistence.dto.article.ArticleSaveDto;
import com.example.newsblog.persistence.dto.article.ArticleUpdateDto;
import com.example.newsblog.persistence.model.Article;
import com.example.newsblog.service.ArticleService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Long save(ArticleSaveDto articleSaveDto) {
        Article article = new Article();
        article.setTitle(articleSaveDto.getTitle());
        article.setContent(articleSaveDto.getContent());
        article.setUser(articleSaveDto.getUser());
        return articleRepository.save(article).getId();
    }

    @Override
    public void update(ArticleUpdateDto articleUpdateDto) {
        Article article = articleRepository.findById(articleUpdateDto.getId()).orElse(null);
        article.setTitle(articleUpdateDto.getTitle());
        article.setContent(articleUpdateDto.getContent());
        articleRepository.save(article);
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Article getById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getAllByCriteria(ArticleCriteria criteria) {
        return articleRepository.findAll(
                Specification
                        .where(ArticleSpecification.getArticleByUsername(criteria.getUsername()))
                        .and(ArticleSpecification.getArticlesByTitle(criteria.getTitle()))
        );
    }
}
