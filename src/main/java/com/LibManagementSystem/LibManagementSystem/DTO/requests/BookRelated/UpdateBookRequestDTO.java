package com.LibManagementSystem.LibManagementSystem.DTO.requests.BookRelated;

import com.LibManagementSystem.LibManagementSystem.models.BookRelated.BookStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookRequestDTO {
    private String bookGenre;
    private BookStatus bookStatus;
}
