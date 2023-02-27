package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.book.links.Book2GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Book2GenreRepository extends JpaRepository<Book2GenreEntity, Integer> {
    public List<Integer> getAllByGenreId(int authorId);
}
