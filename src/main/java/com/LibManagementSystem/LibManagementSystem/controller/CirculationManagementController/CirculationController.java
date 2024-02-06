package com.LibManagementSystem.LibManagementSystem.controller.CirculationManagementController;


import com.LibManagementSystem.LibManagementSystem.DTO.responses.BookResponse;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.Book;
import com.LibManagementSystem.LibManagementSystem.service.CirculationManagementService.CirculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class CirculationController {

    private final CirculationService circulationService;



    @GetMapping()
    public ResponseEntity<List<BookResponse>> getAllBooks(

            @RequestParam(name = "page", defaultValue = "1") int page

    ){
        return ResponseEntity.ok(circulationService.getAllBooksService(page, 3));
    }




    // borrow a book
@GetMapping(path ="/borrow-book/{bookId}")
    public ResponseEntity<BookResponse> borrowBook(@PathVariable("bookId") Integer bookId){
        return ResponseEntity.ok(circulationService.borrowBookService(bookId));
}




    // return book
    @PostMapping(path="/return-book/{bookId}")

    public ResponseEntity<List<BookResponse>> returnBook(@PathVariable("bookId") Integer bookId , @RequestBody List<Book> listOfBooksReturned){
        return ResponseEntity.ok(circulationService.returnBookService(bookId, listOfBooksReturned));
    }



    //borrow period renewal request
}
