package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long userid;

    private String name;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private User_Type type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookid")
    private Book bookId;

    @JsonIgnore
    @OneToOne(mappedBy = "userid",cascade = CascadeType.ALL)
    Record record;

    @OneToMany(mappedBy = "bookid",cascade = CascadeType.ALL)
    Set<Book> bookList = new HashSet<>();

    @JsonIgnore
    public Set<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void removeBook(Book book) {
        bookList.remove(book);
    }



}
