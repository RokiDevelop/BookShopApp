package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.getReferenceById(id);
    }

    public List<Book> getRandomBooksData() {
        List<Book> list = getBooksData();
        Collections.shuffle(list);
        return list;
    }

    public List<Book> getBooksByAuthor(Author author) {

        Iterator<Book> iterator = bookRepository.findByAuthorId(author.getId()).iterator();
        //TODO: create select booksByAuthor
        List<Book> books = new ArrayList<>();
        iterator.forEachRemaining(books::add);
        return books;
    }

    public List<Book> getRecommendedBooks() {
        //TODO: edit result list by recommendation;
        List<Book> books;
        books = getBooksData();
        return books;
    }


    public List<Book> getPopularBooks() {
        //TODO: edit result list by popular;
        List<Book> books;
        books = getBooksData();
        return books;
    }

    public List<Book> getRecentBooks() {
        //TODO: edit result list by recent;
        List<Book> books;
        books = getBooksData();
        return books;
    }
}
