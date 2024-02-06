package com.LibManagementSystem.LibManagementSystem;

import com.LibManagementSystem.LibManagementSystem.models.BookRelated.Book;
import com.LibManagementSystem.LibManagementSystem.models.BookRelated.BookStatus;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.UserRole;
import com.LibManagementSystem.LibManagementSystem.repo.BookRepo;
import com.LibManagementSystem.LibManagementSystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LibManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibManagementSystemApplication.class, args);
    }


    @Value("${application.env.creds.admin-email}")
    private String ADMIN_EMAIL;

    @Value("${application.env.creds.admin-pass}")
    private String ADMIN_PASSWORD;
    //admin user

    @Bean
    CommandLineRunner run(UserRepo userRepo, BookRepo bookRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepo.findByEmail(ADMIN_EMAIL).isPresent()) {

                return;
            }

            List<Book> listOfDefaultBooks = new ArrayList<>();


            //page 1
            Book someBook = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("123123123").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();

            Book someBook2 = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("111232323").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();
            Book someBook3 = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("67856756").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();


            //page 2
            Book someBook4 = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("123324132").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();
            Book someBook5 = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("512453245").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();
            Book someBook6 = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("346256547346").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();
//page 3
            Book someBook7 = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("dfdfdcsafas").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();
            Book someBook8 = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("ssssss").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();
            Book someBook9 = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("asgasfg").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();

            listOfDefaultBooks.add(someBook);
            listOfDefaultBooks.add(someBook2);
            listOfDefaultBooks.add(someBook3);
            listOfDefaultBooks.add(someBook4);
            listOfDefaultBooks.add(someBook5);
            listOfDefaultBooks.add(someBook6);
            listOfDefaultBooks.add(someBook7);
            listOfDefaultBooks.add(someBook8);
            listOfDefaultBooks.add(someBook9);


            User admin =

                    User.builder().firstName("admin").lastName("user").
                            email(ADMIN_EMAIL).password(passwordEncoder.encode(ADMIN_PASSWORD)).
                            userRole(UserRole.ADMIN).userEnabled(true).build();

            bookRepo.saveAll(listOfDefaultBooks);
            userRepo.save(admin);
        };
    }
}
