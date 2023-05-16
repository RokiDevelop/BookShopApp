package com.example.bookshopapp.data.user;

import com.example.bookshopapp.data.enums.ContactType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of user contact entity", value = "UserContact")
public class UserContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_contact_seq")
    @SequenceGenerator(name = "user_contact_seq", allocationSize = 1)
    private int id;

    @Column(columnDefinition = "INT NOT NULL")
    private int userId;

    private ContactType type;

    @Column(columnDefinition = "SMALLINT NOT NULL")
    private short approved;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String code;

    @Column(columnDefinition = "INT")
    private int codeTrails;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime codeTime;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String contact;
}
