package com.example.bookshopapp.data.book.rating;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.user.UserEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ApiModel(description = "data model of book rating value ", value = "BookRating")
@NoArgsConstructor
@Table(name = "book_rating", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
public class BookRating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_rating_seq")
    @SequenceGenerator(name = "book_rating_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(columnDefinition = "TIMESTAMP(6) NOT NULL")
    private LocalDateTime time;

    @Column(name = "value",columnDefinition = "SMALLINT", nullable = false)
    private Byte value;

    public BookRating(UserEntity user, Book book, LocalDateTime time, Byte value) {
        this.user = user;
        this.book = book;
        this.time = time;
        this.value = value;
    }
}
