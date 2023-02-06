package com.example.bookshopapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/genres")
public class GenresPageController {

    @GetMapping("")
    public String genresPage(){
        return "genres/index";
    }

    @GetMapping("/{id}")
    public String genresById(@PathVariable(value = "id") int id, Model model){
        return "genres/slug";
    }
}