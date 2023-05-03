package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query(value = "SELECT a.* FROM book2author b2a " +
            "JOIN author a ON b2a.author_id = a.id " +
            "WHERE b2a.book_id = :book_id",
            countQuery = "SELECT count(*) FROM Book2Author b2a WHERE b2a.book_id = :book_id",
            nativeQuery = true)
    List<Author> findByBooks(@Param("book_id")int bookId);
}
