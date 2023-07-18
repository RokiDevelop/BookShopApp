package com.example.bookshopapp.data.user;

import com.example.bookshopapp.data.book.links.Book2UserEntity;
import com.example.bookshopapp.data.book.links.User2UserDataSecurity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of user entity", value = "User")
public class UserEntity {

    public UserEntity(String name, LocalDateTime regTime, String hash, int balance) {
        this.name = name;
        this.regTime = regTime;
        this.hash = hash;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @Column(columnDefinition = "TIMESTAMP(6) NOT NULL")
    private LocalDateTime regTime;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(columnDefinition = "INT NOT NULL")
    private int balance;

    @OneToOne(mappedBy = "userEntity")
    private User2UserDataSecurity user2UserDataSecurity;

    @OneToMany(mappedBy = "user")
    private List<Book2UserEntity> book2UserEntities;
}
