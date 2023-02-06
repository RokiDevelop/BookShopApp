package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("select Book2AuthorEntity.bookId from Book2AuthorEntity b where b.authorId = ?1")  //TODO: ERROR!!!!
    Set<Integer> findBooksByAuthorId(Integer id);

    @Query(value = "SELECT b FROM Book b WHERE b.id = :ids")
    List<Book> findBooksByIdList(@Param("ids") Collection<Integer> booksId);
}
