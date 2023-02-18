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
public class Book implements Serializable {

    @Id
    @GeneratedValue
    @NotNull
    private Long bookid;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User userid;

    private String author;

    private String publication;

    @Enumerated(EnumType.STRING)
    private Book_category category;

    @Column(nullable=false)
    private boolean isAvailable = Boolean.TRUE;

    public boolean isAvailable(){
        return isAvailable;
    }

    private LocalDateTime issue_date;

    private LocalDateTime return_date;

    @JsonIgnore
    @OneToMany(mappedBy = "userid",cascade = CascadeType.ALL)
    Set<User> waitingList = new HashSet<>();

    public void addUser(User user) {
        waitingList.add(user);
    }

    public void removeUser(User user) {
        waitingList.remove(user);
    }

    public Set<User> getWaitingList() {
        return waitingList;
    }

}
