package com.LibManagementSystem.LibManagementSystem.DTO.requests.BookRelated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequestDTO {
    private String bookISBN;
    private String bookName;
    private String bookAuthor;
    private String bookGenre;
    private Integer publishedYear;
}
