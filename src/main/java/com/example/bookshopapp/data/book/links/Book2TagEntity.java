package com.example.bookshopapp.data.book.links;


import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.tag.TagEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book2tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of book to tag entity", value = "Book2Tag")
public class Book2TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_2_tag_seq")
    @SequenceGenerator(name = "book_2_tag_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;
}
