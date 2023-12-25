package com.LibManagementSystem.LibManagementSystem.controller.CirculationManagementController;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class CirculationController {
    @GetMapping(path = "/test-circulation-management")
    public String test(){
        return "list of Books here";
    }




    // borrow book

    // return book

    //borrow period renewal request
}
