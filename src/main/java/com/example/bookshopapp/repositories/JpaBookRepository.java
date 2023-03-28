package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaBookRepository extends JpaRepository<Book, Integer>, BookRepository {

    @Query(value = "SELECT b from Book b JOIN Book2TagEntity b2t ON b.id = b2t.book.id where b2t.tag.id = :id")
    Page<Book> findAllByTagId(@Param("id") Long id, Pageable pageable);

    @Query(value = "SELECT b " +
            "FROM Book b " +
            "left join Book2AuthorEntity b2a ON b2a.bookId = b.id " +
            "WHERE b2a.authorId = :author_id")
    Page<Book> findBooksByAuthorId(@Param("author_id") Integer authorId, Pageable nextPage);

    @Query(value = "FROM Book WHERE isBestseller = 1")
    List<Book> findBooksByIsBestseller();

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceBetween(Integer min, Integer max);

    List<Book> findBooksByPriceIs(Integer price);

    @Query(value = "SELECT b FROM Book b WHERE b.pubDate between :min_date AND :max_date ORDER BY b.pubDate")
    Page<Book> findBooksByPubDateBetweenOrdOrderByPubDate(@Param("min_date") LocalDateTime minDate, @Param("max_date") LocalDateTime maxDate, Pageable nextPage);

    List<Book> findBooksByDiscountBetween(Byte min, Byte max);

    @Query(value = "SELECT b FROM Book b")
    List<Book> getRecommendedBooks();

    @Query(value = "SELECT b FROM Book b")
    List<Book> getRecentBooks();

    @Query(value = "SELECT b.* " +
            "FROM book2genre b2g " +
            "JOIN book b ON b2g.book_id = b.id " +
                "WHERE b2g.genre_id = :genre_id", nativeQuery = true)
    Page<Book> getBookByGenreId(@Param("genre_id") Integer genre_id, Pageable nextPage);
}
