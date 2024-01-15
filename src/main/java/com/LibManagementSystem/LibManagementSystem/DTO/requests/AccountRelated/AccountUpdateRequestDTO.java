package com.LibManagementSystem.LibManagementSystem.DTO.requests.AccountRelated;

import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountUpdateRequestDTO {
    private String newEmail;
    private String newPassword;
    private String newFirstname;
    private String newLastname;
    private String confirmPassword;

}
