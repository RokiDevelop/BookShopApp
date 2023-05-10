package com.example.bookshopapp.repositories;


import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.book.rating.BookRating;
import com.example.bookshopapp.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IBookRatingRepository extends JpaRepository<BookRating, Long> {
    List<BookRating> findBookRatingsByBookId(Integer id);

    BookRating findBookRatingByUserAndBook(UserEntity user, Book book);

    List<BookRating> findBookRatingByBookId(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update BookRating set value = :value where id = :id")
    void updateValueById(@Param("id") Long id, @Param("value") byte value);
}
