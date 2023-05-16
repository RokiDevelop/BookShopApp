package com.example.bookshopapp.data.book.links;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book2user_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of book to user type entity", value = "Book2UserType")
public class Book2UserTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_2_user_type_seq")
    @SequenceGenerator(name = "book_2_user_type_seq", allocationSize = 1)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String code;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;
}
