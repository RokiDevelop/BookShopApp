package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.dto.BookReviewDto;
import com.example.bookshopapp.security.data.UserDataSecurityDetails;
import com.example.bookshopapp.services.BookReviewService;
import com.example.bookshopapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookReviewController {
    private final BookReviewService bookReviewService;
    private final BookService bookService;

    @Autowired
    public BookReviewController(BookReviewService bookReviewService, BookService bookService) {
        this.bookReviewService = bookReviewService;
        this.bookService = bookService;
    }

    @ModelAttribute(name = "reviewList")
    public List<BookReviewDto> getBookReview() {
        return new ArrayList<>();
    }

    @PostMapping("/bookReview")
    public String addBookReview(@RequestParam("bookId") Integer bookId,
                                @RequestParam("text") String text,
                                Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDataSecurityDetails userDetails = (UserDataSecurityDetails) authentication.getPrincipal();
            Integer userId = userDetails.getUserDataSecurity().getId();

            Book book = bookService.getBookById(bookId);
            LocalDateTime time = LocalDateTime.now();

            bookReviewService.saveReview(bookId, userId, time, text);

            return ("redirect:/books/" + book.getSlug());
        }

        return "redirect:/signin";
    }
}
