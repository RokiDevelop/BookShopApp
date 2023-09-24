package com.example.bookshopapp.controllers;

import com.example.bookshopapp.security.data.UserDataSecurityDetails;
import com.example.bookshopapp.services.BookReviewLikeService;
import com.example.bookshopapp.services.BookReviewService;
import com.example.bookshopapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.time.LocalDateTime;

@Controller
public class BookReviewLikeController {
    private final BookReviewLikeService bookReviewLikeService;
    private final BookReviewService bookReviewService;
    private final BookService bookService;

    @Autowired
    public BookReviewLikeController(BookReviewLikeService bookReviewLikeService, BookReviewService bookReviewService, BookService bookService) {
        this.bookReviewLikeService = bookReviewLikeService;

        this.bookReviewService = bookReviewService;
        this.bookService = bookService;
    }

    @PostMapping("/rateBookReview")
    public String addBookReviewLike(@RequestParam(value = "value") Integer value,
                                    @RequestParam(value = "reviewId") Integer reviewId,
                                    Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            UserDataSecurityDetails userDetails = (UserDataSecurityDetails) authentication.getPrincipal();
            Integer userId = userDetails.getUserDataSecurity().getId();
            LocalDateTime time = LocalDateTime.now();

            short shortValue = (short) min(max(value, Short.MIN_VALUE), Short.MAX_VALUE);

            bookReviewLikeService.saveBookReviewLike(reviewId, userId, time, shortValue);

            Integer bookId = bookReviewService.getBookIdByReviewId(reviewId);
            String bookSlug = bookService.getBookById(bookId).getSlug();
            return "redirect:/books/" + bookSlug;
        }

        return "redirect:/signin";
    }

}
