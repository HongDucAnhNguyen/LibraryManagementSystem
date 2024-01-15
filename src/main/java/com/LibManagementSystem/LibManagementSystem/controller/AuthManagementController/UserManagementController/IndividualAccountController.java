package com.LibManagementSystem.LibManagementSystem.controller.AuthManagementController.UserManagementController;

import com.LibManagementSystem.LibManagementSystem.DTO.requests.AccountRelated.AccountUpdateRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.responses.AccountResponse;
import com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.IndividualAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user/my-account")
@RequiredArgsConstructor
public class IndividualAccountController {

    private final IndividualAccountService accountService;


    //get my account details

    @GetMapping(path = "/")
    public ResponseEntity<AccountResponse> getAccountDetails() {

        return ResponseEntity.ok(accountService.getAccountDetails());
    }


//update my account
    @PutMapping(path = "/update-account")

    public ResponseEntity<AccountResponse> updateAccount(

            @RequestBody AccountUpdateRequestDTO updateData) {


        return ResponseEntity.ok(accountService.updateMyAccountService(updateData));

    }



   /*
    disable my account {
    +. verify current user to be disabled match
    current logged in token provided

    +. verifyConfirmPassword()

    +. delete account


    }
     */

@GetMapping(path = "/disable-account")
    public ResponseEntity<AccountResponse> disableMyAccount(){

        return ResponseEntity.ok(accountService.disableMyAccountService());

    }




   /*
    lock my account {
    +. verify current user to be disabled match
    current logged in token provided

    +. verifyConfirmPassword()

    +. delete account


    }
     */


    /*
    delete my account {
    +. verify current user to be deleted match
    current logged in token provided

    +. verifyConfirmPassword()

    +. delete account


    }
     */






}
