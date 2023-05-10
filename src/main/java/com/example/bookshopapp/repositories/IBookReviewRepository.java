package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookReviewRepository extends JpaRepository<BookReviewEntity, Integer> {
    List<BookReviewEntity> findBookReviewEntitiesByBookId(Integer bookId);


}
