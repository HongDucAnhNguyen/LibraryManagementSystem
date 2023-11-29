package com.LibManagementSystem.LibManagementSystem.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class BooksController {
    @GetMapping(path = "/all-books")
    public String allBooks(){
        return "list of Books here";
    }
}
