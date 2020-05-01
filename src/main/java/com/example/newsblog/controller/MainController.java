package com.example.newsblog.controller;

import com.example.newsblog.persistence.dto.article.ArticleCriteria;
import com.example.newsblog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    private ArticleService articleService;

    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ModelAttribute("criteria")
    public ArticleCriteria articleCriteria() {
        return new ArticleCriteria();
    }

    @GetMapping({"/home", "/"})
    public String getIndexPage(Model model, @ModelAttribute("criteria") ArticleCriteria criteria) {
        model.addAttribute("articles", articleService.getAllByCriteria(criteria));
        return "index";
    }

}
