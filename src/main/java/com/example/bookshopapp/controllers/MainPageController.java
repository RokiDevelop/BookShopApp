package com.example.bookshopapp.controllers;

import com.example.bookshopapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String mainPage(Model model){
        model.addAttribute("bookDataRecommendation", bookService.getRandomBooksData());
        model.addAttribute("bookDataRecent", bookService.getRandomBooksData());
        model.addAttribute("bookDataPopular", bookService.getBooksData());
        return "index";
    }

    @GetMapping("/index")
    public String mainPage(){
        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchBook(){
        return "redirect:/";
    }

}
