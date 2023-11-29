package com.LibManagementSystem.LibManagementSystem.DTO;


import com.LibManagementSystem.LibManagementSystem.models.Token;
import com.LibManagementSystem.LibManagementSystem.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    private String token;

}
