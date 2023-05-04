package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.repositories.ResourceStorage;
import com.example.bookshopapp.data.dto.AbstractBooksDto;
import com.example.bookshopapp.data.dto.BookDto;
import com.example.bookshopapp.data.dto.PopularBooksDto;
import com.example.bookshopapp.data.dto.RecommendedBookDto;
import com.example.bookshopapp.services.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
@Api(description = "books data")
public class BooksPageController {

    private final BookService bookService;
    private final BooksRatingAndPopularService popularService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final TagService tagService;
    private final ResourceStorage storage;


    @Autowired
    public BooksPageController(BookService bookService,
                               AuthorService authorService,
                               BooksRatingAndPopularService popularService, GenreService genreService, TagService tagService, ResourceStorage storage) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.popularService = popularService;
        this.genreService = genreService;
        this.tagService = tagService;
        this.storage = storage;
    }

    @GetMapping("/recent")
    public String recentPage(@RequestParam(value = "from", required = false) String optDateFrom,
                             @RequestParam(value = "to", required = false) String optDateTo,
                             @RequestParam(value = "offset", required = false) Integer offset,
                             @RequestParam(value = "limit", required = false) Integer limit,
                             Model model) {

        AbstractBooksDto booksDto =  new PopularBooksDto(bookService.getPageOfRecentBooks(
                optDateFrom, optDateTo, offset, limit).getContent());
        model.addAttribute("bookDataRecent", booksDto);
        return "books/recent";
    }

    @GetMapping("/popular")
    public String popularPage(@RequestParam(value = "offset", required = false) Integer offset,
                              @RequestParam(value = "limit", required = false) Integer limit,
                              Model model) {

        int currentOffset = offset == null ? 0 : offset;
        int currentLimit = limit == null ? 20 : limit;

        AbstractBooksDto booksDto =  new PopularBooksDto(popularService.getBooksByRatingAndPopular(currentOffset, currentLimit).getContent());

        model.addAttribute("bookDataPopular",booksDto);

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
        AbstractBooksDto booksDto =  new BookDto( bookService.getPageBooksByAuthor(author, offset.orElse(0), limit.orElse(20)).getContent());

        model.addAttribute("author", author);
        model.addAttribute("booksData", booksDto);
        return "books/author";
    }


    @GetMapping("/genre/{id}")
    public String genresById(@PathVariable(value = "id") Integer id, Model model){
        AbstractBooksDto booksDto =  new BookDto( bookService.getBooksByGenreId(id, null, null).getContent());

        model.addAttribute("category", genreService.getGenreById(id).getName());
        model.addAttribute("booksData", booksDto);
        return "genres/slug";
    }

    @GetMapping("/{slug}")
    public String book(@PathVariable(value = "slug") String slug, Model model) throws Exception {
        Book book = bookService.getBookBySlug(slug);
        List<Author> authors = authorService.findAuthorsByBookId(book);

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        return "books/slug";
    }

    @GetMapping("/my")
    public String my() {
        return "books/slugmy";
    }

    @GetMapping("/tag/{id}")
    public String tagById(@PathVariable(value = "id") Long id,
                          @RequestParam(value = "offset", required = false) Integer offset,
                          @RequestParam(value = "limit", required = false) Integer limit,
                          Model model) {
        model.addAttribute("booksData", tagService.getBooksByTagId(id, offset, limit).getContent());
        return "tags/index";
    }

    @GetMapping("/api")
    @ApiOperation(value = "method to get list of books")
    @ResponseBody
    public List<Book> booksApi() {
        return bookService.getBooksData();
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {

        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookService.getBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookService.save(bookToUpdate); //save new path in db here

        return ("redirect:/books/" + slug);
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash")String hash) throws IOException{
        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: "+path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: "+mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: "+data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }
}
