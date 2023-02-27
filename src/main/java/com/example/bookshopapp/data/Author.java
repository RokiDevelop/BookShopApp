package com.example.bookshopapp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "author")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "data model of author entity")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(value = "author id generated by db", position = 1)
    private Integer id;

    @Column(name = "photo", columnDefinition = "VARCHAR(255)")
    private String photo;

    @Column(name = "slug", columnDefinition = "VARCHAR(255)", nullable = false)
    private String slug;

    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
    @ApiModelProperty(value = "full name of author", example = "Bob Smith", position = 2)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @ApiModelProperty(value = "any information about author", example = "Bob was born in San Francisco and lived there for 10 years", position = 3)
    private String description;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
