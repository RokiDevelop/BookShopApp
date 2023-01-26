package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.services.AuthorService;
import com.example.bookshopapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/authors")
public class AuthorsPageController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AuthorsPageController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ModelAttribute(value = "authorsMap")
    public Map<String, List<Author>> authorsMap() {
        Map<String, List<Author>> resultMap = authorService.getAuthorMapByLetter();
        return resultMap;
    }

    @GetMapping("")
    public String authors() {
        return "authors/index";
    }

    @GetMapping(value = "/{id}")
    public String authorById(@PathVariable(value = "id") int id, Model model) throws Exception {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        model.addAttribute("books", bookService.getBooksByAuthor(author));
        return "authors/slug";
    }
}
