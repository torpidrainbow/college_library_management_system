package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "records")
public class Record {
    @Id
    private Long id;

    private Long books_issued;

    private Long books_reserved;

    private Long books_returned;

    private Long fine_amount;

}
