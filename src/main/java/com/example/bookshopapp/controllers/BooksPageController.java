package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.services.AuthorService;
import com.example.bookshopapp.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/books")
public class BooksPageController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BooksPageController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/recent")
    public String recentPage(Model model) {
        model.addAttribute("category", "Новинки");
        model.addAttribute("booksList", bookService.getRecentBooks());
        return "books/recent";
    }

    @GetMapping("/popular" )
    public String popularPage(Model model) {
        model.addAttribute("booksList", bookService.getPopularBooks());
        return "books/popular";
    }

    @GetMapping("/recommended")
    public String recommendedBook(Model model) {
        model.addAttribute("booksList", bookService.getRecommendedBooks());
        //TODO: set return string
        return null;
    }

    @GetMapping("/author/{id}")
    public String booksByAuthor(@PathVariable(value = "id") int id, Model model) throws Exception {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        model.addAttribute("booksListByAuthor", bookService.getBooksByAuthor(author));
        return "books/author";
    }

    @GetMapping("/genre/{id}")
    public String booksGenreById(@PathVariable(value = "id") int id, Model model) {
        //TODO: set return string
        return null;
    }

    @GetMapping("/{id}")
    public String book(@PathVariable(value = "id") int id, Model model) throws Exception {
        //TODO: set return string
        model.addAttribute("book", bookService.getBookById(id));
        return "books/slug";
    }

    @GetMapping("/my")
    public String my() {
        return "books/slugmy";
    }

    @GetMapping("/tag/{id}")
    public String tagById(@PathVariable(value = "id") int id, Model model) {
        //TODO: set return string
        return null;
    }
}
