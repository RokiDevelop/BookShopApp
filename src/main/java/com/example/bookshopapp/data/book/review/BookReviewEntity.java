package com.example.bookshopapp.data.book.review;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of book review entity" , value ="BookReview")
public class BookReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_review_seq")
    @SequenceGenerator(name = "book_review_seq", allocationSize = 1)
    private int id;

    @Column(columnDefinition = "INT NOT NULL")
    private int bookId;

    @Column(columnDefinition = "INT NOT NULL")
    private int userId;

    @Column(columnDefinition = "TIMESTAMP(6) NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;
}
