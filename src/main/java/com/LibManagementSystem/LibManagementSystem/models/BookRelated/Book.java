package com.LibManagementSystem.LibManagementSystem.models.BookRelated;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;


    @Column(unique = true, nullable = false)
    private String bookISBN;

    @Column(nullable = false)
    private String bookName;
    @Column(nullable = false)
    private String bookAuthor;
    @Column( nullable = false)
    private String bookGenre;
    @Column( nullable = false)
    private Integer publishedYear;



    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus; //borrowed/available





//structure book entity


}
