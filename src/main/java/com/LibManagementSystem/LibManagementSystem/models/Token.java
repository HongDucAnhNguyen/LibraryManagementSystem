package com.LibManagementSystem.LibManagementSystem.models;

import com.LibManagementSystem.LibManagementSystem.models.TokenType;
import com.LibManagementSystem.LibManagementSystem.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue
    private Integer tokenId;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

   private boolean expired;
    private boolean revoked;
    @ManyToOne
    //many tokens to one programmer
    @JoinColumn(name = "user_id")
    private User user;
}
