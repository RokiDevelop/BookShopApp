package com.example.bookshopapp.data.genre;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "genre")
@Getter
@Setter
@ApiModel(description = "data model of genre entity", value = "Genre")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_seq")
    @SequenceGenerator(name = "genre_seq", allocationSize = 1)
    private Integer id;

    @Column(columnDefinition = "INT")
    private Integer parentId;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
