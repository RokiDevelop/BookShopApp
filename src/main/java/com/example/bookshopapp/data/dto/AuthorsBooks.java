package com.example.bookshopapp.data.dto;

import com.example.bookshopapp.data.Book;

import java.util.List;

public class AuthorsBooks extends IBooksDto {
    private int count;
    private List<Book> books;

    public AuthorsBooks(List<Book> books) {
        this.books = books;
        this.count = books.size();
    }
}
