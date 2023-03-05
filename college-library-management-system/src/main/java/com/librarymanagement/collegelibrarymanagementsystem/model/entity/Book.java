package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import lombok.Data;

import javax.persistence.*;

import java.util.*;


@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookid;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User borrower;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publication;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Book_category category;

    @Column(nullable=false)
    private boolean isAvailable = Boolean.TRUE;

    public Book() {

    }
    public boolean isAvailable(){
        return isAvailable;
    }

    private Date issue_date;

    private Date dueDate;

    private Date renewalDate;


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
            return (int) ((currentDate.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24));
        } else {
            return 0;
        }
    }

    public Book(String title, String author, String publisher,Book_category category) {
        this.title = title;
        this.author = author;
        this.publication = publisher;
        this.category = category;
    }

}
