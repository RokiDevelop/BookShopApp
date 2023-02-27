package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "FROM Book b WHERE b.id = :ids")
    List<Book> findBooksByIdList(@Param("ids") Collection<Integer> booksId);

    List<Book> findBooksByPubDateBetween(LocalDateTime min, LocalDateTime max);

    @Query(value = "FROM Book WHERE isBestseller = 1")
    List<Book> findBooksByIsBestseller();
}
