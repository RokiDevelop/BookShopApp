package com.example.bookshopapp.data.dto;

import com.example.bookshopapp.data.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class BookDto extends AbstractBooksDto {
    private int count;
    private List<Book> books;

    public BookDto(List<Book> books) {
        this.books = books;
        this.count = books.size();
    }
}
