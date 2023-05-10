package com.example.bookshopapp.data.book.links;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book2author")
@Getter
@Setter
@ApiModel(description = "data model of book to author entity", value = "Book2Author")
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Book2AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int sortIndex;
}
