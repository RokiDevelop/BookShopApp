package com.example.bookshopapp.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(mappedBy = "bookFile")
    @JoinColumn(name = "book_id")
    private Book book;

    @JoinColumn(name = "data")
    private String data;

    @Override
    public String toString() {
        return "BookFile{" +
                "id=" + id +
                ", user=" + book +
                ", data='" + data + '\'' +
                '}';
    }
}
