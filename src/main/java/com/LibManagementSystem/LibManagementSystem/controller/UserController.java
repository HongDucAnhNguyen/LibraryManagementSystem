package com.LibManagementSystem.LibManagementSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @GetMapping(path = "/all-users")
    public String allUsers(){
        return "list of Users here";
    }
}
