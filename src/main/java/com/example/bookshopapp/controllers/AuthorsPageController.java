package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.dto.AuthorsBooks;
import com.example.bookshopapp.data.dto.IBooksDto;
import com.example.bookshopapp.services.AuthorService;
import com.example.bookshopapp.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
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

    @GetMapping("/author/{id}")
    @ResponseBody
    public IBooksDto authorById(@PathVariable(value = "id") int id,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        Author author = authorService.getAuthorById(id);
        return new AuthorsBooks( bookService.getPageBooksByAuthor(author, offset, limit).getContent());
    }

    @GetMapping("")
    public String authorsPage() {
        return "authors/index";
    }

    @GetMapping(value = "/{id}")
    public String authors(@PathVariable(value = "id") int id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        model.addAttribute("books", bookService.getPageBooksByAuthor(author,0, 6).getContent());
        return "authors/slug";
    }

    @GetMapping("/api")
    @ApiOperation("method to get map of authors")
    @ResponseBody
    public Map<String, List<Author>> authorsApi() {
        return authorService.getAuthorMapByLetter();
    }
}
