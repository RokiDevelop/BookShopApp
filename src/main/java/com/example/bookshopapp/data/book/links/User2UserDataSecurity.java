package com.example.bookshopapp.data.book.links;

import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.security.data.UserDataSecurity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user2user_data_security")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of user to AuthData this user", value = "User2UserDataSecurity")
public class User2UserDataSecurity {

    public User2UserDataSecurity(UserEntity user, UserDataSecurity userDataSecurity) {
        this.userEntity = user;
        this.userDataSecurity = userDataSecurity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_entity")
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "UserDataSecurity")
    private UserDataSecurity userDataSecurity;
}
