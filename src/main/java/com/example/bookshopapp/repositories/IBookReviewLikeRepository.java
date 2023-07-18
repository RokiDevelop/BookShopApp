package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookReviewLikeRepository extends JpaRepository<BookReviewLikeEntity, Integer> {

    List<BookReviewLikeEntity> findBookReviewLikeEntitiesByReviewId(Integer reviewId);
}
