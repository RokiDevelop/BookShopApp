package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(value = "SELECT a " +
            "FROM Author a " +
            "left join Book2AuthorEntity b2a ON a.id = b2a.authorId " +
            "WHERE b2a.bookId = :book_id")
    List<Author> findAuthorsByBookId(@Param("book_id") int bookId);
}
