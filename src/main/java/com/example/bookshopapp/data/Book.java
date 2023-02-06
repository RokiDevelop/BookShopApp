package com.example.bookshopapp.data;

import com.example.bookshopapp.data.book.links.Book2AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "pub_date", nullable = false)
    private Date pubDate;

    @Column(name = "is_bestseller", columnDefinition = "SMALLINT", nullable = false)
    private byte isBestseller;

    @Column(name = "slug", columnDefinition = "VARCHAR(255)", nullable = false)
    private String slug;

    @Column(name = "title", columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Column(name = "image", columnDefinition = "VARCHAR(255)")
    private String image;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "discount", columnDefinition = "SMALLINT default 0", nullable = false)
    private byte discount;

    @OneToMany(mappedBy = "bookId")
    private Set<Book2AuthorEntity> book2AuthorSet;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", pubDate=" + pubDate +
                ", isBestseller=" + isBestseller +
                ", slug='" + slug + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", discount='" + discount + '\'' +
                ", price='" + price + '\'' +
                ", book2AuthorSet=" + book2AuthorSet +
                '}';
    }
}
