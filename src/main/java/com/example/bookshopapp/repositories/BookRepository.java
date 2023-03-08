package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT b " +
            "FROM Book b " +
            "left join Book2AuthorEntity b2a ON b.id = b2a.bookId " +
            "WHERE b2a.authorId = :author_id")
    Page<Book> findBooksByAuthorId(@Param("author_id") Integer authorId, Pageable nextPage);

    @Query(value = "FROM Book WHERE isBestseller = 1")
    List<Book> findBooksByIsBestseller();

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceBetween(Integer min, Integer max);

    List<Book> findBooksByPriceIs(Integer price);

//    List<Book> findBooksByPubDateBetween(LocalDateTime minDate, LocalDateTime maxDate);

    Page<Book> findBooksByPubDateBetween(LocalDateTime minDate, LocalDateTime maxDate, Pageable nextPage);

    List<Book> findBooksByDiscountBetween(Byte min, Byte max);

    @Query(value = "SELECT b FROM Book b")
    List<Book> getRecommendedBooks();

    @Query(value = "SELECT b FROM Book b")
    List<Book> getPopularBooks();

    @Query(value = "SELECT b FROM Book b")
    List<Book> getRecentBooks();
}
