package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import lombok.Data;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue
    @NotNull
    private Long bookid;

    private String title;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User borrower;

    private String author;

    private String publication;

    @Enumerated(EnumType.STRING)
    private Book_category category;

    @Column(nullable=false)
    private boolean isAvailable = Boolean.TRUE;

    public boolean isAvailable(){
        return isAvailable;
    }

    private Date issue_date;

    private Date due_date;

    private Date return_date;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "book_waiting_list",
    joinColumns = @JoinColumn(name = "bookId"),
    inverseJoinColumns = @JoinColumn(name = "userId"))
    List<User> waitingList;

    public void addUser(User user) {
        waitingList.add(user);
    }

    public void removeUser() {

        waitingList.remove(0);
    }

    public List<User> getWaitingList() {
        return waitingList;
    }


}
