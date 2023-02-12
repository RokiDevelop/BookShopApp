package com.example.bookshopapp.data.book.file;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
@Getter
@Setter
public class BookFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "hash", columnDefinition = "VARCHAR(255)", nullable = false)
    private String hash;

    @ManyToOne
    @JoinColumn(name = "type_id", columnDefinition = "INT", nullable = false, referencedColumnName = "id")
    private BookFileTypeEntity typeId;

    @Column(name = "path", columnDefinition = "VARCHAR(255)", nullable = false)
    private String path;
}
