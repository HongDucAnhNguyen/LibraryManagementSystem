package com.LibManagementSystem.LibManagementSystem.DTO.responses;

import com.LibManagementSystem.LibManagementSystem.models.BookRelated.BookStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {
    private String bookISBN;
    private String bookName;
    private String bookAuthor;
    private String bookGenre;
    private Integer publishedYear;
    private BookStatus bookStatus;
    private String responseMessage;
}
