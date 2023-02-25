package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.*;


@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookid;

    @NotNull
    @NotEmpty
    private String title;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User borrower;

    @NotNull
    @NotEmpty
    private String author;

    @NotNull
    @NotEmpty
    private String publication;

    @NotNull
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Book_category category;

    @Column(nullable=false)
    private boolean isAvailable = Boolean.TRUE;

    public boolean isAvailable(){
        return isAvailable;
    }

    @Temporal(TemporalType.DATE)
    private Date issue_date;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    private Date return_date;

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    private Date renewalDate;



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

    public int daysOverdue() {
        Date currentDate = new Date();
        if (currentDate.after(dueDate)) {
            return (int) ((currentDate.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24)); // convert milliseconds to days
        } else {
            return 0;
        }
    }

}
