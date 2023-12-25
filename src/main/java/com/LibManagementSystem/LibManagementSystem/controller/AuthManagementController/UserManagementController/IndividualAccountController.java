package com.LibManagementSystem.LibManagementSystem.controller.AuthManagementController.UserManagementController;

import com.LibManagementSystem.LibManagementSystem.DTO.responses.AccountResponse;
import com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.IndividualAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user/my-account")
@RequiredArgsConstructor
public class IndividualAccountController {

    private final IndividualAccountService accountService;




    //get my account details

    @GetMapping("/")
    public ResponseEntity<AccountResponse> getAccountDetails(HttpServletRequest request, HttpServletResponse response)  throws IOException{

        return ResponseEntity.ok(accountService.getAccountDetails(request, response));
    }




   /*
    update my account {
    +. verify current user to be updated match
    current logged in token provided

    +. verifyConfirmPassword()

    +. update account


    }
     */

   /*
    disable my account {
    +. verify current user to be disabled match
    current logged in token provided

    +. verifyConfirmPassword()

    +. delete account


    }
     */
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




    //private helper method verifyConfirmPassword()



}
