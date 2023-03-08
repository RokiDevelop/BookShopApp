package com.example.bookshopapp.data.book.links;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book2author")
@Getter
@Setter
@ApiModel(description = "data model of book to author entity", value = "Book2Author")
@Embeddable
public class Book2AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_id",columnDefinition = "INT NOT NULL")
    private int bookId;

    @Column(name = "author_id", columnDefinition = "INT NOT NULL")
    private int authorId;

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int sortIndex;
}
