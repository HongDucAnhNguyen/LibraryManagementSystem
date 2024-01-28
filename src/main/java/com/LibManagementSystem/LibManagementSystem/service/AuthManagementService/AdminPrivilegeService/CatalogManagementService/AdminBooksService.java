package com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.AdminPrivilegeService.CatalogManagementService;

import com.LibManagementSystem.LibManagementSystem.DTO.requests.BookRelated.AddBookRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.requests.BookRelated.UpdateBookRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.responses.BookResponse;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.Book;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.BookStatus;
import com.LibManagementSystem.LibManagementSystem.repo.BookRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBooksService {

    private final BookRepo bookRepo;


    public BookResponse addBookService(AddBookRequestDTO newBookBody) {


        Book newBook = Book.builder().bookAuthor(newBookBody.getBookAuthor()).bookGenre(newBookBody.getBookGenre()).bookISBN(newBookBody.getBookISBN())
                .bookName(newBookBody.getBookName()).publishedYear(newBookBody.getPublishedYear()).bookStatus(BookStatus.AVAILABLE).build();

        bookRepo.save(newBook);


        return BookResponse.builder().bookAuthor(newBook.getBookAuthor())
                .bookGenre(newBook.getBookGenre()).bookISBN(newBook.getBookISBN()).bookName(newBook.getBookName())
                .publishedYear(newBook.getPublishedYear()).bookStatus(newBook.getBookStatus()).responseMessage("new book added").build();
    }


    public BookResponse removeBookService(Integer bookId) {

        Book bookFound = bookRepo.findById(bookId).orElseThrow();
        bookRepo.deleteById(bookId);
        return BookResponse.builder().bookAuthor(bookFound.getBookAuthor())
                .bookGenre(bookFound.getBookGenre()).bookISBN(bookFound.getBookISBN()).bookName(bookFound.getBookName())
                .publishedYear(bookFound.getPublishedYear()).bookStatus(bookFound.getBookStatus()).responseMessage("book removed").build();
    }

    public BookResponse updateBookService(@NonNull UpdateBookRequestDTO updateData, Integer bookId) {
        Book bookFound = bookRepo.findById(bookId).orElseThrow();

        bookFound.builder().bookGenre(updateData.getBookGenre()).bookStatus(updateData.getBookStatus());
        bookRepo.save(bookFound);
        return BookResponse.builder().bookAuthor(bookFound.getBookAuthor())
                .bookGenre(bookFound.getBookGenre()).bookISBN(bookFound.getBookISBN()).bookName(bookFound.getBookName())
                .publishedYear(bookFound.getPublishedYear()).bookStatus(bookFound.getBookStatus()).responseMessage("book updated").build();
    }
}
