package com.example.bookshopapp.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "first_name")
    private String firstName;

    @JoinColumn(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author")
    private Set<Book2Author> book2AuthorSet;

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
