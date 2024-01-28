package com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.AdminPrivilegeService;

import com.LibManagementSystem.LibManagementSystem.DTO.responses.UserResponse;
import com.LibManagementSystem.LibManagementSystem.models.JwtTokenRelated.Token;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.UserRole;
import com.LibManagementSystem.LibManagementSystem.repo.TokenRepo;
import com.LibManagementSystem.LibManagementSystem.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminUserAccountService {

    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;

    public List<User> getAllUsersService() {

        return userRepo.findAll();
    }


    public String removeUserService(Integer userId) {
        User userRetrieved = userRepo.findById(userId).orElseThrow();
        if (userRetrieved.getUserRole().equals(UserRole.ADMIN)) {
            throw new IllegalStateException("cannot delete admin account");
        }
        List<Token> tokensOfUser = tokenRepo.findAllValidTokensByUser(userId);
        tokensOfUser.forEach(
                token -> {
                    tokenRepo.deleteById(token.getTokenId());
                }
        );
        userRepo.deleteById(userId);
        return "deletion successful";
    }

    public UserResponse getUserByIdService(Integer userId) {


        User userRetrieved =  userRepo.findById(userId).orElseThrow();

        if (userRetrieved == null){
            throw new NullPointerException();
        }

        return UserResponse.builder().email(userRetrieved.getEmail()).userEnabled(userRetrieved.isEnabled()).firstName(
                userRetrieved.getFirstName()
        ).lastName(userRetrieved.getLastName()).build();
    }

    public void disableUserAccountService(Integer userId) {

        User userRetrieved = userRepo.findById(userId).orElseThrow();
        userRetrieved.setUserEnabled(false);
        userRepo.save(userRetrieved);


    }
}
