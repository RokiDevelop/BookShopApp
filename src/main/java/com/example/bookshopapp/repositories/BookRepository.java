package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("select b from Book b") //TODO: change the query, but first link book and authors tables
    Iterable<Book> findByAuthorId(Integer id);
}
