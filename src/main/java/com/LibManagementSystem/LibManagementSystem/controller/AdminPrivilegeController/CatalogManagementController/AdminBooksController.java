package com.LibManagementSystem.LibManagementSystem.controller.AdminPrivilegeController.CatalogManagementController;

import com.LibManagementSystem.LibManagementSystem.DTO.requests.AddBookRequestDTO;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.Book;
import com.LibManagementSystem.LibManagementSystem.service.AdminPrivilegeService.CatalogManagementService.AdminBooksService;
import com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/library-system")
@RequiredArgsConstructor
public class AdminBooksController {

    private final AdminBooksService booksService;


    @GetMapping(path = "/tester")
    public String adminOnly() {
        return "admin access only";
    }
//add a new book to library

    @PostMapping(path = "/new-book")
    public ResponseEntity<Book> addBookToLib(@RequestBody @NonNull AddBookRequestDTO newBookBody) {
        return ResponseEntity.ok(booksService.addBookService(newBookBody));

    }
// update book details in library
    /*location of book, category*/

    //remove book from library

    //handle user renewal request for book





}
