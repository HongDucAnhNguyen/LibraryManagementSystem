package com.LibManagementSystem.LibManagementSystem.service.CirculationManagementService;

import com.LibManagementSystem.LibManagementSystem.DTO.responses.BookResponse;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.Book;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.BookStatus;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import com.LibManagementSystem.LibManagementSystem.models.borrowedStatusRelated.borrowedStatus;
import com.LibManagementSystem.LibManagementSystem.repo.BookRepo;
import com.LibManagementSystem.LibManagementSystem.repo.borrowedStatusRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CirculationService {

    private final BookRepo bookRepo;
    private final borrowedStatusRepo issueEntryRepo;

    public List<BookResponse> getAllBooksService() {
        List<Book> allBooks = bookRepo.findAll();
        List<BookResponse> allBooksResponse = new ArrayList<>();
        allBooks.forEach(book -> {
            BookResponse bookResponse = BookResponse.builder().bookAuthor(book.getBookAuthor()).bookGenre(book.getBookGenre())
                    .bookISBN(book.getBookISBN()).bookName(book.getBookName()).publishedYear(book.getPublishedYear())
                    .bookStatus(book.getBookStatus()).build();
            allBooksResponse.add(bookResponse);

        });

        return allBooksResponse;

    }


    //user borrows book service
    /*
     * issue a book for a user, this book is then marked borrowed in bookStatus
     * entry for a new issue saved to _borrowed_status
     * record issue timestamp
     * record user and bookid
     * timestamp used for when user returns book to determine an overdued or in time return
     * */

    public BookResponse borrowBookService(Integer bookId) {




        Book bookFound = bookRepo.findById(bookId).orElseThrow();


        if(bookFound.getBookStatus().equals(BookStatus.BORROWED)){

            return BookResponse.builder().responseMessage("book is already borrowed").build();

        }



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userFound = (User) authentication.getPrincipal();


        Date issueTimeStampRaw = new Date();

        // Convert Date to LocalDate
        Instant instant = issueTimeStampRaw.toInstant();
        LocalDate issueTimeStampLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        // Add two weeks to the original date
        LocalDate twoWeeksLater = issueTimeStampLocalDate.plusWeeks(2);

        borrowedStatus issueEntry = borrowedStatus.builder().userIssuedId(userFound.getUserId())
                .bookIssuedId(bookId).issuedTimeStamp(issueTimeStampLocalDate).expectedReturnDate(twoWeeksLater).build();

        bookFound.setBookStatus(BookStatus.BORROWED);

        bookRepo.save(bookFound);
        issueEntryRepo.save(issueEntry);

        return BookResponse.builder().bookAuthor(bookFound.getBookAuthor())
                .bookGenre(bookFound.getBookGenre()).bookISBN(bookFound.getBookISBN()).bookName(bookFound.getBookName())
                .publishedYear(bookFound.getPublishedYear()).bookStatus(bookFound.getBookStatus()).responseMessage("book borrowed").build();
    }

    public List<BookResponse> returnBookService(Integer bookId, List<Book> listOfBooksReturned) {

        List<BookResponse> listOfBooksReturnedResponse = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userFound = (User) authentication.getPrincipal();

        listOfBooksReturned.forEach(book -> {
            borrowedStatus issueEntryFound = issueEntryRepo.findByBookIdAndUserId(bookId, userFound.getUserId());
            Book bookFound = bookRepo.findById(bookId).orElseThrow();
            LocalDate currentDate = LocalDate.now();
            if (currentDate.compareTo(issueEntryFound.getExpectedReturnDate()) > 0) {

                issueEntryFound.setReturnedTimeStamp(currentDate);
                issueEntryFound.setReturnOverdue(true);

            }
            bookFound.setBookStatus(BookStatus.AVAILABLE);


            bookRepo.save(bookFound);
            issueEntryRepo.save(issueEntryFound);

            BookResponse bookReturnedResponseObj = BookResponse.builder().bookAuthor(bookFound.getBookAuthor())
                    .bookGenre(bookFound.getBookGenre()).bookISBN(bookFound.getBookISBN()).bookName(bookFound.getBookName())
                    .publishedYear(bookFound.getPublishedYear()).bookStatus(bookFound.getBookStatus()).responseMessage("book returned").build();


            listOfBooksReturnedResponse.add(bookReturnedResponseObj);

        });


        /*
         * returns the book,
         * entry with the userid and bookid retrieved from repo
         * compare current date with timestamp, if more than 2 weeks had passed, mark book as overdued
         * mark book as available in bookRepo
         *
         * */

        return listOfBooksReturnedResponse;
    }


}
