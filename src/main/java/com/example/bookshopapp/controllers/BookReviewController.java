package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.dto.BookReviewDto;
import com.example.bookshopapp.services.BookReviewLikeService;
import com.example.bookshopapp.services.BookReviewService;
import com.example.bookshopapp.services.BookService;
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
    private final BookReviewLikeService bookReviewLikeService;
    private final BookService bookService;

    public BookReviewController(BookReviewService bookReviewService, BookReviewLikeService bookReviewLikeService, BookService bookService) {
        this.bookReviewService = bookReviewService;
        this.bookReviewLikeService = bookReviewLikeService;
        this.bookService = bookService;
    }

    @ModelAttribute(name = "reviewList")
    public List<BookReviewDto> getBookReview() {
        return new ArrayList<>();
    }

    @PostMapping("/bookReview")
    public String addBookReview(@RequestParam("bookId") Integer bookId,
                                @RequestParam("text") String text) {
        Book book = bookService.getBookById(bookId);

        boolean authorized = true;
        if (!authorized) {
            return "singin";
        }
        Integer userId = 2;
        LocalDateTime time = LocalDateTime.now();

        bookReviewService.saveReview(bookId, userId, time, text);

        return ("redirect:/books/" + book.getSlug());
    }
}
