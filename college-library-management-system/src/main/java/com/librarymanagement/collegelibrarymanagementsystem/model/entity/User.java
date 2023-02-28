package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private User_Type type;



    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    Record record ;


    @OneToMany(mappedBy = "borrower",fetch = FetchType.LAZY)
    List<Book> bookList ;

    public User() {
    }

    public User(String name, String username, String password, User_Type type) {
        this.name = name;
        this.username=username;
        this.password = password;
        this.type = type;
    }

    @Column(nullable = false)
    private int time_period;


    public void addBook(Book book) {
        bookList.add(book);
    }

    public void removeBook(Book book) {
        bookList.remove(book);
    }

    @Column(nullable=false)
    private boolean isActive = Boolean.TRUE;

    public boolean isActive(){
        return isActive;
    }


}
