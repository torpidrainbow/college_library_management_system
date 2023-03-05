package com.librarymanagement.collegelibrarymanagementsystem.model.dto;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BookDto {

    private Long bookid;


    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String publication;


    private Book_category category;

    public BookDto(String title, String author, String publication, Book_category category) {
        this.title = title;
        this.author = author;
        this.publication = publication;
        this.category = category;
    }
    public BookDto() {

    }
}
