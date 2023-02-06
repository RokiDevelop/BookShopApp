package com.example.bookshopapp.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "pub_date")
    private Date pubDate;

    private boolean isBestseller;

    private String slug;

    @JoinColumn(name = "title")
    private String title;

    private String image;

    private String description;

    @JoinColumn(name = "discount")
    private String discount;

    @JoinColumn(name = "price")
    private String price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_file_id", referencedColumnName = "id")
    private BookFile bookFile;

    @OneToMany(mappedBy = "book")
    private Set<Book2Author> book2AuthorSet;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
