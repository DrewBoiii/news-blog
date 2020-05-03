package com.example.newsblog.controller;

import com.example.newsblog.persistence.dto.article.ArticleCriteriaDto;
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

    private final ArticleService articleService;

    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ModelAttribute("criteria")
    public ArticleCriteriaDto articleCriteria() {
        return new ArticleCriteriaDto();
    }

    @GetMapping({"/home", "/"})
    public String getIndexPage(Model model,
                               @ModelAttribute("criteria") ArticleCriteriaDto criteria,
                               @PageableDefault(value = ITEMS_PER_PAGE) Pageable pageable) {
        Sort sort = Sort.by(criteria.getSort()).descending();
        if(criteria.getOrder().equals("asc")) {
            sort = Sort.by(criteria.getSort()).ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        model.addAttribute("page", articleService.getAll(criteria, pageRequest));
        return "index";
    }

}
