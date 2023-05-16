package com.example.bookshopapp.data.book.links;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book2genre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of book to genre entity", value = "Book2Genre")
public class Book2GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_2_genre_seq")
    @SequenceGenerator(name = "book_2_genre_seq", allocationSize = 1)
    private Integer id;

    @Column(columnDefinition = "INT NOT NULL")
    private Integer bookId;

    @Column(columnDefinition = "INT NOT NULL")
    private Integer genreId;
}
