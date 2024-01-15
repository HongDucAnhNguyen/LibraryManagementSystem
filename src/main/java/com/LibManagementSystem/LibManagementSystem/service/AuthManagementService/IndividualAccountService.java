package com.LibManagementSystem.LibManagementSystem.service.AuthManagementService;

import com.LibManagementSystem.LibManagementSystem.DTO.requests.AccountRelated.AccountUpdateRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.responses.AccountResponse;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import com.LibManagementSystem.LibManagementSystem.repo.TokenRepo;
import com.LibManagementSystem.LibManagementSystem.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class IndividualAccountService {
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;


    private boolean isIdValid(Integer id) {

        return userRepo.existsById(id);
    }

    public AccountResponse updateMyAccountService( AccountUpdateRequestDTO updateData) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userFound = (User) authentication.getPrincipal();


        if (!isIdValid(userFound.getUserId())) {
            return AccountResponse.builder().resMessage("user not found").build();
        }

        boolean verifyPassword = verifyConfirmPasswordService(userFound.getEmail(), updateData.getConfirmPassword());
        if (verifyPassword != true) {
            return AccountResponse.builder().resMessage("Error, passwords do not match").build();
        }
        userFound.setEmail(updateData.getNewEmail());
        userFound.setFirstName(updateData.getNewFirstname());
        userFound.setLastName(updateData.getNewLastname());
        userFound.setPassword(passwordEncoder.encode(updateData.getNewPassword()));

        userRepo.save(userFound);


        return AccountResponse.builder().accountName(userFound.getFirstName() + " " + userFound.getLastName())
                .accountEmail(userFound.getEmail()).resMessage("account updated").build();

    }

    public AccountResponse getAccountDetails() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userFound = (User) authentication.getPrincipal();

        return AccountResponse.builder().accountEmail(userFound.getEmail()).
                accountName(userFound.getFirstName() + " " + userFound.getLastName()).build();


    }

    private boolean verifyConfirmPasswordService(String userEmail, String confirmPassword) {
        String userPassword = userRepo.findByEmail(userEmail).orElseThrow().getPassword();

        boolean passwordsMatch = passwordEncoder.matches(confirmPassword, userPassword);

        return passwordsMatch;
    }

    public AccountResponse disableMyAccountService() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userFound = (User) authentication.getPrincipal();
        userFound.setUserEnabled(false);
        userRepo.save(userFound);

        return AccountResponse.builder().resMessage("your account is disabled").build();


    }
}
