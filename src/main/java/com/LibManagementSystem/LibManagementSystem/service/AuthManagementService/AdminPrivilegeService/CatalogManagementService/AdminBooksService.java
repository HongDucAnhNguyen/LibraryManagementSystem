package com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.AdminPrivilegeService.CatalogManagementService;

import com.LibManagementSystem.LibManagementSystem.DTO.requests.BookRelated.AddBookRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.requests.BookRelated.UpdateBookRequestDTO;
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




    public Book addBookService(AddBookRequestDTO newBookBody) {


        Book newBook = Book.builder().bookAuthor(newBookBody.getBookAuthor()).bookGenre(newBookBody.getBookGenre()).bookISBN(newBookBody.getBookISBN())
                .bookName(newBookBody.getBookName()).publishedYear(newBookBody.getPublishedYear()).bookStatus(BookStatus.AVAILABLE).build();

        return bookRepo.save(newBook);
    }



    public String removeBookService(Integer bookId){

         bookRepo.deleteById(bookId);
         return "book removed";
    }

    public Book updateBookService(@NonNull UpdateBookRequestDTO updateData, Integer bookId) {
         Book bookRetrieved = bookRepo.findById(bookId).orElseThrow();

         bookRetrieved.builder().bookGenre(updateData.getBookGenre()).bookStatus(updateData.getBookStatus());
         return bookRepo.save(bookRetrieved);
    }
}
