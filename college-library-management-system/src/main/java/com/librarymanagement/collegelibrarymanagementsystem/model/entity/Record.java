package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "records")
public class Record{
    @Id
    @GeneratedValue
    private Long recordId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer books_issued=0;

    private double fine_amount=0;

}
