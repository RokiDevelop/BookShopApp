package com.example.bookshopapp.data.book.file;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.enums.BookFileType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
@Getter
@Setter
@ApiModel(description = "data model of book file entity", value = "BookFile")
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

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public String getBookFileExtensionString() {
        return BookFileType.getStringByTypeId(typeId.getId());
    }
}
