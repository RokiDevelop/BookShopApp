package com.example.bookshopapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/genres")
public class GenresPageController {

    @GetMapping("/index")
    public String genresPage(){
        return "/genres/index";
    }

    @GetMapping("/{id}")
    public String genres(){
        return "/genres/slug";
    }
}
