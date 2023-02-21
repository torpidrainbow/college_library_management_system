package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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

    private Integer books_reserved=0;

    private Integer books_returned=0;

    private double calculate_fine=0;

}
