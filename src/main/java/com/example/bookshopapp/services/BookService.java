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
    public List<Book> getBooksData() {
        Iterator<Book> iterator = bookRepository.findAll().iterator();
        List<Book> books = new ArrayList<>();
        iterator.forEachRemaining(books::add);
        return books;
    }

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBookById(int id) throws Exception {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        }
        throw new Exception();
    }

    public List<Book> getRandomBooksData() {
        List<Book> list = getBooksData();
        Collections.shuffle(list);
        return new ArrayList<>(list);
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
