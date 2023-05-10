package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.services.BookRatingService;
import com.example.bookshopapp.services.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Controller
@Api
public class RatingController {

    private final BookRatingService bookRatingService;
    private final BookService bookService;

    @Autowired
    public RatingController(BookRatingService bookRatingService, BookService bookService) {
        this.bookRatingService = bookRatingService;
        this.bookService = bookService;
    }

    @PostMapping("/rateBook")
    public String addBookRating(@RequestParam(value = "bookId", required = false) Integer bookId,
                                @RequestParam(value = "value", required = false) Integer value) {

        boolean authorized = true;
        if (!authorized) {
            return "redirect:/";
        }

        byte byteValue = (byte) min(max(value, Byte.MIN_VALUE), Byte.MAX_VALUE);

        Integer userId = 2;
        LocalDateTime time = LocalDateTime.now();

        bookRatingService.saveBookRating(userId, bookId, time, byteValue);

        Book book = bookService.getBookById(bookId);

        return ("redirect:/books/" + book.getSlug());
    }
}
