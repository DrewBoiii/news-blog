package com.example.newsblog.controller;

import com.example.newsblog.persistence.dto.article.ArticleSaveDto;
import com.example.newsblog.persistence.dto.article.ArticleUpdateDto;
import com.example.newsblog.persistence.model.Article;
import com.example.newsblog.service.ArticleService;
import com.example.newsblog.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private ArticleService articleService;
    private UserService userService;

    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping("/articles/{id}")
    public String getArticlePage(Model model, @PathVariable("id") Long id) {
        Article article = articleService.getById(id);
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/editor")
    public String getEditorPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("art_save_dto", new ArticleSaveDto());
        return "editor";
    }

    @PostMapping("/editor/save")
    public String saveArticle(@ModelAttribute("art_save_dto") ArticleSaveDto articleSaveDto,
                              @AuthenticationPrincipal User authUser) {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(authUser.getUsername());
        articleSaveDto.setUser(user);
        Long id = articleService.save(articleSaveDto);
        return "redirect:/articles/" + id;
    }

    @PostMapping("/articles/{id}/update")
    public String updateArticle(@PathVariable("id") Long id,
                                @ModelAttribute("art_upd_dto") ArticleUpdateDto articleUpdateDto,
                                @AuthenticationPrincipal User authUser) {
        articleUpdateDto.setId(id);
        articleService.update(articleUpdateDto);
        return "redirect:/editor";
    }

}
