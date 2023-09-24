package com.example.bookshopapp.security.data;

import com.example.bookshopapp.data.book.links.User2UserDataSecurity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "users_data_security")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDataSecurity {

    public UserDataSecurity(
            String name,
            String email,
            String phone,
            String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type")
    private AuthenticationType authType;

    private String name;
    private String email;
    private String phone;
    private String password;

    @OneToOne(mappedBy = "userDataSecurity")
    private User2UserDataSecurity user2userDataSecurity;
}
