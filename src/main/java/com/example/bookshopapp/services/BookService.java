package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.dto.*;
import com.example.bookshopapp.repositories.AuthorRepository;
import com.example.bookshopapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.getReferenceById(id);
    }

    public Page<Book> getPageBooksByAuthor(Author author, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByAuthorId(author.getId(), nextPage);
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
//                bookRepository.findBooksRecommended(nextPage).getContent() //TODO:need to make query
    }

    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
//                bookRepository.findBooksPopular(nextPage).getContent() //TODO:need to make query
    }

    public Page<Book> getPageOfRecentBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
//                bookRepository.findBooksRecent(nextPage).getContent() //TODO:need to make query
    }

    public Page<Book> getPageOfSearchResultBooks(LocalDateTime minDate, LocalDateTime maxDate, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBooksByPubDateBetween(minDate, maxDate, nextPage);
    }
}
