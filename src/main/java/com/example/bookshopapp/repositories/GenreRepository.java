package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.book.links.Book2GenreEntity;
import com.example.bookshopapp.data.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    @Query(value = "SELECT b2e from Book2GenreEntity b2e ")
    List<Book2GenreEntity> getBookWithGenre();
}
