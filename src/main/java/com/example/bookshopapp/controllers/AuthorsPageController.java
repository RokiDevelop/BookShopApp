package com.example.bookshopapp.controllers;

import com.example.bookshopapp.services.AbcService;
import com.example.bookshopapp.services.AuthorService;
import com.example.bookshopapp.data.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorsPageController {

    private final AbcService abcService;
    private final AuthorService authorService;

    @Autowired
    public AuthorsPageController(AbcService abcService, AuthorService authorService) {
        this.abcService = abcService;
        this.authorService = authorService;
    }

    @GetMapping("/index")
    public String authorPage(Model model) {
        model.addAttribute("mapAbcAuthors", authorService.getAuthorMapByLetter(Language.ENG));
        return "/authors/index";
    }
}
