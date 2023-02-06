package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFileRepository extends JpaRepository<BookFile, Long> {
}
