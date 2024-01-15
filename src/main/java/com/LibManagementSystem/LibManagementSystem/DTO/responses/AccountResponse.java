package com.LibManagementSystem.LibManagementSystem.DTO.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    @JsonProperty("account_email")
    private String accountEmail;
    @JsonProperty("account_name")

    private String accountName;

    @JsonProperty("account_res_message")
    private String resMessage;


}
