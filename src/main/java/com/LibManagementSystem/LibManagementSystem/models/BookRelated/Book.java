package com.LibManagementSystem.LibManagementSystem.models.BookRelated;

import jakarta.persistence.*;
import lombok.*;

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
    private String bookISBN;
    private String bookName;
    private String bookAuthor;
    private String bookGenre;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus; //borrowed/available


//structure book entity


}
