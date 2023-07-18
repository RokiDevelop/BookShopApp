package com.example.bookshopapp.services;

import com.example.bookshopapp.data.book.review.BookReviewEntity;
import com.example.bookshopapp.data.book.review.BookReviewLikeEntity;
import com.example.bookshopapp.data.dto.BookReviewDto;
import com.example.bookshopapp.repositories.IBookReviewLikeRepository;
import com.example.bookshopapp.repositories.IBookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BookReviewService {
    private final IBookReviewRepository bookReviewRepository;
    private final IBookReviewLikeRepository bookReviewLikeRepository;

    @Autowired
    public BookReviewService(IBookReviewLikeRepository bookReviewLikeRepository, IBookReviewRepository bookReviewRepository) {
        this.bookReviewLikeRepository = bookReviewLikeRepository;
        this.bookReviewRepository = bookReviewRepository;
    }

    public List<BookReviewDto> getBookReview(Integer bookId) {
        List<BookReviewDto> reviewDtoList = new ArrayList<>();

        List<BookReviewEntity> bookReviewEntityList = bookReviewRepository.findBookReviewEntitiesByBookId(bookId);

        bookReviewEntityList.forEach(bookReview -> {
            BookReviewDto bookReviewDto = new BookReviewDto();

            bookReviewDto.setBookReviewEntity(bookReview);

            List<BookReviewLikeEntity> bookReviewLikeEntityList =
                    bookReviewLikeRepository.findBookReviewLikeEntitiesByReviewId(bookReview.getId());

            bookReviewLikeEntityList.forEach(reviewLike -> {
                if (reviewLike.getValue() == 1) {
                    bookReviewDto.addPositiveReviewLike(reviewLike);
                } else if (reviewLike.getValue() == -1) {
                    bookReviewDto.addNegativeReviewLike(reviewLike);
                } else throw new IllegalArgumentException();
            });

            reviewDtoList.add(bookReviewDto);
        });

        Stream<BookReviewDto> sorted = reviewDtoList.stream()
                .sorted(Comparator.comparing(BookReviewDto::getRating).reversed());

        return sorted.toList();
    }

    public void saveReview(Integer bookId, Integer userId, LocalDateTime time, String text) {
        BookReviewEntity bookReviewEntity = new BookReviewEntity();
        bookReviewEntity.setBookId(bookId);
        bookReviewEntity.setUserId(userId);
        bookReviewEntity.setText(text);
        bookReviewEntity.setTime(time);
        bookReviewRepository.save(bookReviewEntity);
    }

    public Integer getBookIdByReviewId(Integer reviewId) {
        return bookReviewRepository.findFirstById(reviewId).getBookId();
    }
}
