package com.example.bookshopapp.services;

import com.example.bookshopapp.data.book.review.BookReviewLikeEntity;
import com.example.bookshopapp.repositories.IBookReviewLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookReviewLikeService {

   private final IBookReviewLikeRepository reviewLikeRepository;

   @Autowired
   public BookReviewLikeService(IBookReviewLikeRepository reviewLikeRepository) {
      this.reviewLikeRepository = reviewLikeRepository;
   }

   public void saveBookReviewLike(Integer reviewId, Integer userId, LocalDateTime time, Short value){
      BookReviewLikeEntity bookReviewLikeEntity = new BookReviewLikeEntity();
      bookReviewLikeEntity.setReviewId(reviewId);
      bookReviewLikeEntity.setTime(time);
      bookReviewLikeEntity.setValue(value);
      bookReviewLikeEntity.setUserId(userId);
      reviewLikeRepository.save(bookReviewLikeEntity);
   }
}
