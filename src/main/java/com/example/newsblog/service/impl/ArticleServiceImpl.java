package com.example.newsblog.service.impl;

import com.example.newsblog.persistence.dto.article.ArticleCriteriaDto;
import com.example.newsblog.specification.ArticleSpecification;
import com.example.newsblog.persistence.dao.ArticleRepository;
import com.example.newsblog.persistence.dto.article.ArticleSaveDto;
import com.example.newsblog.persistence.dto.article.ArticleUpdateDto;
import com.example.newsblog.persistence.model.Article;
import com.example.newsblog.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long save(ArticleSaveDto dto) {
        Article article = modelMapper.map(dto, Article.class);
        return articleRepository.save(article).getId();
    }

    @Override
    public void update(ArticleUpdateDto dto) {
        Article article = articleRepository.findById(dto.getId()).orElse(null);
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setEditedAt(LocalDateTime.now());
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
    public Page<Article> getAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> getAll(ArticleCriteriaDto criteria, Pageable pageable) {
        return articleRepository.findAll(
                Specification
                        .where(ArticleSpecification.getArticleByUsername(criteria.getUsername()))
                        .and(ArticleSpecification.getArticlesByTitle(criteria.getTitle())),
                pageable
        );
    }
}
