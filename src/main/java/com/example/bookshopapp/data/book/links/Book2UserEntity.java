package com.example.bookshopapp.data.book.links;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book2user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of book to user entity", value = "Book2User")
public class Book2UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_2_user_seq")
    @SequenceGenerator(name = "book_2_user_seq", allocationSize = 1)
    private int id;

    @Column(columnDefinition = "TIMESTAMP(6) NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "INT NOT NULL")
    private int typeId;

    @Column(columnDefinition = "INT NOT NULL")
    private int bookId;

    @Column(columnDefinition = "INT NOT NULL")
    private int userId;
}
