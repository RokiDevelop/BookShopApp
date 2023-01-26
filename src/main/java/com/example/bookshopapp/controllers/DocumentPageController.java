package com.example.bookshopapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documents")
public class DocumentPageController {

    @GetMapping("")
    public String documentsPage() {
        return "documents/index";
    }


    @GetMapping("/{id}")
    public String documentById(@PathVariable(value = "id") int id, Model model) {
        return "documents/slug";
    }

}
