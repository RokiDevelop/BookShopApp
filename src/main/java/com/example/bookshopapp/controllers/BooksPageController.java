package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.dto.AbstractBooksDto;
import com.example.bookshopapp.data.dto.RecommendedBookDto;
import com.example.bookshopapp.services.AuthorService;
import com.example.bookshopapp.services.BookService;
import com.example.bookshopapp.services.BooksRatingAndPopularService;
import com.example.bookshopapp.services.GenreService;
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
    private final BooksRatingAndPopularService popularService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BooksPageController(BookService bookService,
                               AuthorService authorService,
                               BooksRatingAndPopularService popularService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.popularService = popularService;
        this.genreService = genreService;
    }

    @GetMapping("/recent")
    public String recentPage(@RequestParam(value = "from", required = false) String optDateFrom,
                             @RequestParam(value = "to", required = false) String optDateTo,
                             @RequestParam(value = "offset", required = false) Integer offset,
                             @RequestParam(value = "limit", required = false) Integer limit,
                             Model model) {

        model.addAttribute("bookDataRecent", bookService.getPageOfRecentBooks(
                optDateFrom, optDateTo, offset, limit).getContent());
        return "books/recent";
    }

    @GetMapping("/popular")
    public String popularPage(@RequestParam(value = "offset", required = false) Integer offset,
                              @RequestParam(value = "limit", required = false) Integer limit,
                              Model model) {

        int currentOffset = offset == null ? 0 : offset;
        int currentLimit = limit == null ? 20 : limit;

        model.addAttribute("bookDataPopular",
                popularService.getBooksByRatingAndPopular(currentOffset, currentLimit));

        return "books/popular";
    }

    @GetMapping("/recommended")
    public AbstractBooksDto recommendedBook(@RequestParam(value = "offset", required = false) Integer offset,
                                            @RequestParam(value = "limit", required = false) Integer limit) {
        return new RecommendedBookDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping("/author/{id}")
    public String booksByAuthor(@PathVariable(value = "id") Integer authorId,
                                @RequestParam("offset") Optional<Integer> offset,
                                @RequestParam("limit") Optional<Integer> limit,
                                Model model) {
        Author author = authorService.getAuthorById(authorId);

        Page<Book> bookPage = bookService.getPageBooksByAuthor(authorId, offset.orElse(0), limit.orElse(20));

        model.addAttribute("author", author);
        model.addAttribute("booksData", bookPage.getContent());
        return "books/author";
    }


    @GetMapping("/genre/{id}")
    public String genresById(@PathVariable(value = "id") int id, Model model){
        model.addAttribute("category", genreService.getGenreById(id).getName());
        model.addAttribute("booksData", bookService.getBooksByGenreId(id, null, null).getContent());
        return "genres/slug";
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
