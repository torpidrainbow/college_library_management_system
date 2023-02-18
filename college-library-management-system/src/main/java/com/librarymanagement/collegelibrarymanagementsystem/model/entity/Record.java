package com.librarymanagement.collegelibrarymanagementsystem.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "records")
public class Record implements Serializable {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User userid;

    private Integer books_issued;

    private Integer books_reserved;

    private Integer books_returned;

    private Long calculate_fine;

}
