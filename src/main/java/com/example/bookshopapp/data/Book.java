package com.example.bookshopapp.data;

import com.example.bookshopapp.data.book.file.BookFile;
import com.example.bookshopapp.data.book.links.Book2AuthorEntity;
import com.example.bookshopapp.data.tag.TagEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "data model of book entity")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(value = "book id generated by db")
    private Integer id;

    @Column(name = "pub_date", columnDefinition = "TIMESTAMP(6)",nullable = false)
    private LocalDateTime pubDate;

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

    @OneToMany(mappedBy = "book")
    private List<BookFile> bookFileList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book2author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "book2tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags = new HashSet<>();

    public Integer discountPrice() {
        return price - price * discount / 100;
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getBooks().remove(this);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
