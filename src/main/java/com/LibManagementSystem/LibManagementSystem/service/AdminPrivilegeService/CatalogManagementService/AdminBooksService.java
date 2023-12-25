package com.LibManagementSystem.LibManagementSystem.service.AdminPrivilegeService.CatalogManagementService;

import com.LibManagementSystem.LibManagementSystem.DTO.requests.AddBookRequestDTO;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.Book;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.BookStatus;
import com.LibManagementSystem.LibManagementSystem.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBooksService {

    private final BookRepo bookRepo;

    public Book addBookService(AddBookRequestDTO newBookBody) {


        Book newBook = Book.builder().bookAuthor(newBookBody.getBookAuthor()).bookGenre(newBookBody.getBookGenre()).bookISBN(newBookBody.getBookISBN())
                .bookName(newBookBody.getBookName()).bookStatus(BookStatus.AVAILABLE).build();

        return bookRepo.save(newBook);
    }
}
