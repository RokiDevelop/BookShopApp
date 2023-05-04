package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.dto.*;
import com.example.bookshopapp.services.BookService;
import com.example.bookshopapp.services.BooksRatingAndPopularService;
import com.example.bookshopapp.services.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api
public class MainPageController {

    private final TagService tagService;
    private final BookService bookService;
    private final BooksRatingAndPopularService booksRatingAndPopularService;


    @Autowired
    public MainPageController(TagService tagService, BookService bookService, BooksRatingAndPopularService booksRatingAndPopularService) {
        this.tagService = tagService;
        this.bookService = bookService;
        this.booksRatingAndPopularService = booksRatingAndPopularService;
    }

    @ModelAttribute(value = "bookDataRecommendation")
    public AbstractBooksDto getBookDataPageRecommendation() {
        return new RecommendedBookDto(bookService.getPageOfRecommendedBooks(0, 20).getContent());
    }

    @ModelAttribute(value = "bookDataPagePopular")
    public AbstractBooksDto getBookDataPopular() {
        return new PopularBooksDto(booksRatingAndPopularService.getBooksByRatingAndPopular(0,20).getContent());
    }

    @ModelAttribute(value = "bookDataRecent")
    public AbstractBooksDto getBookDataPageRecent() {
        return new RecentBooksDto(bookService.getPageOfRecentBooks(null, null,0, 20).getContent());
    }

    @ModelAttribute(value = "dataForCloud")
    public List<TagDto> getTagsWithAmount() {
        return tagService.getTagsWithAmount();
    }

    @GetMapping("")
    public String mainPage() {
        return "/index";
    }

    @GetMapping("/signin")
    public String signinPage() {
        return "signin";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/faq")
    public String faqPage() {
        return "faq";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }

    @GetMapping("/transactions")
    public String transactions() {
        //TODO:
        return null;
    }

    @PostMapping("/payment")
    public String payment() {
        return "redirect:/";
    }

    @GetMapping("/my")
    public String my() {
        return "my";
    }

    @PostMapping("/requestContactConfirmation")
    public String requestContactConfirmation() {
        //TODO:
        return null;
    }

    @PostMapping("/approveContact")
    public String approveContact() {
        //TODO:
        return null;
    }

    @PostMapping("/bookReview")
    public String bookReview() {
        //TODO:
        return null;
    }

    @PostMapping("/changeBookStatus")
    public String changeBookStatus() {
        //TODO:
        return null;
    }

    @PostMapping("/rateBookReview")
    public String rateBookReview() {
        //TODO:
        return null;
    }

    @PostMapping("/rateBook")
    public String rateBook() {
        //TODO:
        return null;
    }

    @GetMapping("/search")
    public String search() {
        //TODO:
        return null;
    }

    @GetMapping("/main/api")
    @ApiOperation("")
    public String mainApi() {
        return "";
    }
}
