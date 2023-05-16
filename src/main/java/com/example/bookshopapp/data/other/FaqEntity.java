package com.example.bookshopapp.data.other;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "faq")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of faq entity", value = "Faq")
public class FaqEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_seq")
    @SequenceGenerator(name = "faq_seq", allocationSize = 1)
    private int id;

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int sortIndex;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String question;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String answer;
}
