package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.BookFile;
import com.example.bookshopapp.services.BookFileService;
import com.example.bookshopapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final BookFileService bookFileService;

    @Autowired
    public MainPageController(BookService bookService, BookFileService bookFileService) {
        this.bookService = bookService;
        this.bookFileService = bookFileService;
    }

    @ModelAttribute(value = "bookDataRecommendation")
    public List<Book> getBookDataRecommendation() {
        return bookService.getRandomBooksData();
    }

    @ModelAttribute(value = "bookDataPopular")
    public List<Book> getBookDataPopular() {
        return bookService.getBooksData();
    }

    @ModelAttribute(value = "bookDataRecent")
    public List<Book> getBookDataRecent() {
        return bookService.getRandomBooksData();
    }

    @GetMapping("")
    public String mainPage(){
        BookFile bf = bookFileService.getBookFileById(1L);
        return "/index";
    }

    @GetMapping("/postponed")
    public String postponedPage(){
        return "postponed";
    }

    @GetMapping("/cart")
    public String cartPage(){
        return "cart";
    }

    @GetMapping("/signin")
    public String signinPage(){
        return "signin";
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "about";
    }

    @GetMapping("/faq")
    public String faqPage(){
        return "faq";
    }

    @GetMapping("/contacts")
    public String contacts(){
        return "contacts";
    }

    @GetMapping("/profile")
    public String profilePage(){
        return "profile";
    }

    @GetMapping("/transactions")
    public String transactions(){
        //TODO:
        return null;
    }

    @PostMapping("/payment")
    public String payment(){
        return "redirect:/";
    }

    @GetMapping("/my")
    public String my(){
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


}
