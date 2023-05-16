package com.example.bookshopapp.data.book.file;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "file_download")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of file download entity", value = "FileDownload")
public class FileDownloadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_download_seq")
    @SequenceGenerator(name = "file_download_seq", allocationSize = 1)
    private int id;

    @Column(name = "user_id", columnDefinition = "INT NOT NULL")
    private int userId;

    @Column(columnDefinition = "INT NOT NULL")
    private int bookId;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 1")
    private int count;
}
