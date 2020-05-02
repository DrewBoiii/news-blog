package com.example.newsblog.controller;

import com.example.newsblog.persistence.dto.article.ArticleCriteria;
import com.example.newsblog.service.ArticleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    public static final int ITEMS_PER_PAGE = 5;

    private ArticleService articleService;

    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ModelAttribute("criteria")
    public ArticleCriteria articleCriteria() {
        return new ArticleCriteria();
    }

    @GetMapping({"/home", "/"})
    public String getIndexPage(Model model,
                               @ModelAttribute("criteria") ArticleCriteria criteria,
                               @PageableDefault(value = ITEMS_PER_PAGE, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(criteria.getSort()).descending());
        model.addAttribute("page", articleService.getAll(criteria, pageRequest));
        return "index";
    }

}
