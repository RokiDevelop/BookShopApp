package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.dto.IBooksDto;
import com.example.bookshopapp.data.dto.PopularBooksDto;
import com.example.bookshopapp.data.dto.RecommendedBookDto;
import com.example.bookshopapp.services.AuthorService;
import com.example.bookshopapp.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/books")
@Api(description = "books data")
public class BooksPageController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BooksPageController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/recent")
    public String recentPage(@RequestParam("from") Optional<String> optDateFrom,
                                @RequestParam("to") Optional<String> optDateTo,
                                @RequestParam("offset") Optional<Integer> offset,
                                @RequestParam("limit") Optional<Integer> limit,
                             Model model) {
        Page<Book> bookPage = bookService.getPageOfRecentBooks(
                optDateFrom, optDateTo,
                offset, limit);

        model.addAttribute("bookDataRecent", bookPage.getContent());

        return "books/recent";
    }

    @GetMapping("/popular")
    @ResponseBody
    public IBooksDto popularPage(@RequestParam("offset")  Optional<Integer> offset, @RequestParam("limit") Optional<Integer> limit) {
        return new PopularBooksDto(bookService.getPageOfPopularBooks(offset.orElse(0), limit.orElse(6)).getContent());
    }

    @GetMapping("/recommended")
    @ResponseBody
    public IBooksDto recommendedBook(@RequestParam("offset") Optional<Integer> offset, @RequestParam("limit") Optional<Integer> limit) {
        return new RecommendedBookDto(bookService.getPageOfRecommendedBooks(offset.orElse(0), limit.orElse(6)).getContent());
    }

    @GetMapping("/author/{id}")
    public String booksByAuthor(@PathVariable(value = "id") int id, Model model, @RequestParam("offset") Optional<Integer> offset,
                                @RequestParam("limit") Optional<Integer> limit) {
        Author author = authorService.getAuthorById(id);

        model.addAttribute("author", author);
        model.addAttribute("books", bookService.getPageBooksByAuthor(author, offset.orElse(0), limit.orElse(20)).getContent());
        return "books/author";
    }

    @GetMapping("/genre/{id}")
    public String booksGenreById(@PathVariable(value = "id") int id, Model model) {
        //TODO: set return string
        return null;
    }

    @GetMapping("/{id}")
    public String book(@PathVariable(value = "id") int id, Model model) throws Exception {
        Book book = bookService.getBookById(id);
        List<Author> authors = authorService.findAuthorsByBookId(book.getId());

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
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

    @GetMapping("/api")
    @ApiOperation(value = "method to get list of books")
    @ResponseBody
    public List<Book> booksApi() {
        return bookService.getBooksData();
    }
}
