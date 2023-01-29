package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
//    public User(String name, String user_name, String password, User_Type type) {
//        this.name = name;
//        this.user_name = user_name;
//        this.password = password;
//        this.type = type;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private String user_name;

    private String password;

    @Enumerated(EnumType.STRING)
    private User_Type type;

}
