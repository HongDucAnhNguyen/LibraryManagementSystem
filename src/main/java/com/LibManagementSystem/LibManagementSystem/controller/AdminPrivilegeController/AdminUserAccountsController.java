package com.LibManagementSystem.LibManagementSystem.controller.AdminPrivilegeController;

import com.LibManagementSystem.LibManagementSystem.DTO.responses.GetOneUserResponse;

import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.AdminPrivilegeService.AdminUserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/user-accounts")
public class AdminUserAccountsController {
    private final AdminUserAccountService adminUserAccountService;


    //check out all user accounts
    @GetMapping(path = "/all-users")
    public List<String> allUsers() {
        List<String> userEmails = new ArrayList<>();
        List<User> usersRetrieved = adminUserAccountService.getAllUsersService();
        usersRetrieved.forEach(user -> {
            userEmails.add("Email: " + user.getEmail() + "\n" +
                    "Name: " + user.getFirstName() + " " + user.getLastName());
        });

        return userEmails;
    }


    //get a specific user account
    @GetMapping(path = "/{userId}")
    public ResponseEntity<GetOneUserResponse> getUserById(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(adminUserAccountService.getUserByIdService(userId));
    }


    //disable/lock an account
    @GetMapping(path = "/disable-account/{userId}")
    public String disableUserAccount(@PathVariable("userId") Integer userId) {
        adminUserAccountService.disableUserAccountService(userId);
        return "user disabled";
    }


    //remove a user account
    @DeleteMapping(path = "remove-account/{userId}")
    public String removeUser(@PathVariable Integer userId) {

        adminUserAccountService.removeUserService(userId);

        return "removed user";
    }


    //update an account


}
