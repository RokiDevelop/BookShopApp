package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.dto.BookDto;
import com.example.bookshopapp.data.dto.AbstractBooksDto;
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
@RequestMapping("/authors")
@Api(description = "authors data")
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
        return authorService.getAuthorMapByLetter();
    }

    @GetMapping("")
    public String authorsPage() {
        return "authors/index";
    }

    @GetMapping(value = "/{id}")
    public String authors(@PathVariable(value = "id") Integer authorId,
                                    @RequestParam(value = "offset", required = false) Integer offset,
                                    @RequestParam(value = "limit", required = false) Integer limit,
                                    Model model) {
        Author author = authorService.getAuthorById(authorId);
        AbstractBooksDto booksDto =  new BookDto( bookService.getPageBooksByAuthor(authorId, offset, limit).getContent());

        model.addAttribute("author", author);
        model.addAttribute("booksData", booksDto);

        return "authors/slug";
    }

    @GetMapping("/api")
    @ApiOperation("method to get map of authors")
    @ResponseBody
    public Map<String, List<Author>> authorsApi() {
        return authorService.getAuthorMapByLetter();
    }
}
