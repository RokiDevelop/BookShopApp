package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        Iterator<Book> iterator = bookRepository.findAll().iterator();
        List<Book> books = new ArrayList<>();
        iterator.forEachRemaining(books::add);
        return books;
    }

    public List<Book> getRandomBooksData() {
        List<Book> list = getBooksData();
        Collections.shuffle(list);
        return new ArrayList<>(list);
    }
}
