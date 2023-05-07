package com.example.bookshopapp.controllers;

import com.example.bookshopapp.services.BookReviewLikeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.time.LocalDateTime;

@Controller
public class BookReviewLikeController {
    private final BookReviewLikeService bookReviewLikeService;

    public BookReviewLikeController( BookReviewLikeService bookReviewLikeService) {
        this.bookReviewLikeService = bookReviewLikeService;
    }

    @PostMapping("/rateBookReview")
    public String addBookReviewLike(@RequestParam("reviewId") Integer reviewId,
                                    @RequestParam("value") Integer value) {

        boolean authorized = true;
        if (!authorized) {
            return "singin";
        }
        Integer userId = 2;
        LocalDateTime time = LocalDateTime.now();

        short shortValue = (short) min(max(value, Short.MIN_VALUE), Short.MAX_VALUE);

        bookReviewLikeService.saveBookReviewLike(reviewId, userId, time, shortValue);

        return ("redirect:");
    }

}
