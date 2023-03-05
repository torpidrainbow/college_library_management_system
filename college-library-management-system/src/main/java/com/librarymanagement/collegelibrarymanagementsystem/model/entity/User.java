package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false ,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private User_Type type;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Record record ;


    @OneToMany(mappedBy = "borrower",fetch = FetchType.LAZY)
    List<Book> bookList;

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
