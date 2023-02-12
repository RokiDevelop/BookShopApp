package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.book.links.Book2AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Book2AuthorRepository extends JpaRepository<Book2AuthorEntity, Integer> {
    public List<Integer> getAllByAuthorId(int authorId);
}
