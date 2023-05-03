package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.repositories.JdbcBookRepository;
import com.example.bookshopapp.repositories.JpaBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksRatingAndPopularService {
    private final JdbcBookRepository jdbcBookRepository;

    @Autowired
    public BooksRatingAndPopularService( JdbcBookRepository jdbcBookRepository) {
        this.jdbcBookRepository = jdbcBookRepository;
    }

    public Page<Book> getBooksByRatingAndPopular(Integer offset,
                                      Integer limit){

        Pageable nextPage = PageRequest.of(offset, limit);

        List<Book> resultList = jdbcBookRepository.getPopularBooks(nextPage);

        return new PageImpl<>(resultList, nextPage, jdbcBookRepository.getBooksCount());
    }
}
