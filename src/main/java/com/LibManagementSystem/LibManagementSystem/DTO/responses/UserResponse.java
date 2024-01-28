package com.LibManagementSystem.LibManagementSystem.DTO.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String email;
    private boolean userEnabled;
    private String firstName;
    private String lastName;
}
