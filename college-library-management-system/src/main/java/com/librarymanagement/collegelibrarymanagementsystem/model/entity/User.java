package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User{

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User_Type getType() {
        return type;
    }

    public void setType(User_Type type) {
        this.type = type;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    private String name;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private User_Type type;


    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    Record record;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    List<Book> bookList;

    @JsonIgnore
    public List<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void removeBook(Book book) {
        bookList.remove(book);
    }



}
