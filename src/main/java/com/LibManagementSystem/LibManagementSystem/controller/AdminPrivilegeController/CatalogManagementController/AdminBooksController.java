package com.LibManagementSystem.LibManagementSystem.controller.AdminPrivilegeController.CatalogManagementController;

import com.LibManagementSystem.LibManagementSystem.DTO.requests.BookRelated.AddBookRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.requests.BookRelated.UpdateBookRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.responses.BookResponse;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.Book;
import com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.AdminPrivilegeService.CatalogManagementService.AdminBooksService;
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



    /*replace book with DTOs*/

    @PostMapping(path = "/new-book")
    public ResponseEntity<BookResponse> addBookToLib(@RequestBody @NonNull AddBookRequestDTO newBookBody) {
        return ResponseEntity.ok(booksService.addBookService(newBookBody));

    }

    // update book details in library
    /*location of book, category*/
    @PutMapping(path = "/update-book/{bookId}")
    public ResponseEntity<BookResponse> updateBookInLib(@RequestBody @NonNull UpdateBookRequestDTO updateData, @PathVariable Integer bookId) {
        return ResponseEntity.ok(booksService.updateBookService(updateData, bookId));

    }

    // remove book from library
    @DeleteMapping(path = "/remove-book/{bookId}")
    public ResponseEntity<BookResponse> removeBookFromLib(@PathVariable Integer bookId) {
        return ResponseEntity.ok(booksService.removeBookService(bookId));

    }

    //handle user renewal request for book






}
