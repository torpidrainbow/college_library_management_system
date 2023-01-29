package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    private Long id;

    private String title;

    private String author;

    private String publication;

    @Enumerated(EnumType.STRING)
    private Book_category category;
}
