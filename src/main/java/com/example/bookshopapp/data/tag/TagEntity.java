package com.example.bookshopapp.data.tag;

import com.example.bookshopapp.data.Book;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Tag")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    @SequenceGenerator(name = "tag_seq", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Book> books;
}
