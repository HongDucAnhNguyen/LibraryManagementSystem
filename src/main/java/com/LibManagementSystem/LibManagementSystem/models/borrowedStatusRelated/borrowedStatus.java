package com.LibManagementSystem.LibManagementSystem.models.borrowedStatusRelated;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_borrowed_status")
public class borrowedStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO
    )
    private Integer issueEntryId; //id of the entry for a book issued to a user
    private Integer userIssuedId;
    private Integer bookIssuedId;
    private LocalDate issuedTimeStamp;
    private LocalDate returnedTimeStamp;
    private LocalDate expectedReturnDate; //2 weeks + issuedtime
    private boolean isReturnOverdue;
}
