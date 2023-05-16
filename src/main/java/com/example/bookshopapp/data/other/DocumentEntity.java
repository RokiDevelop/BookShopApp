package com.example.bookshopapp.data.other;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of document entity", value = "Document")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_seq")
    @SequenceGenerator(name = "document_seq", allocationSize = 1)
    private int id;

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int sortIndex;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String title;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;
}
