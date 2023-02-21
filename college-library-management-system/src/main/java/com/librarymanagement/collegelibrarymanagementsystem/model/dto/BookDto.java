package com.librarymanagement.collegelibrarymanagementsystem.model.dto;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BookDto {

    private Long bookid;

    private String title;

    private String author;

    private String publication;

    private Book_category category;
}
