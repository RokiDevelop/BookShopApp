package com.example.bookshopapp.data.dto;

import com.example.bookshopapp.data.Book;

import java.util.List;

public abstract class AbstractBooksDto {
    abstract List<Book> getBooks();

    abstract int getCount();
}
