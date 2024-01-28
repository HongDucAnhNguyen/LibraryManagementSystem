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


            Book someBook = Book.builder().
                    bookStatus(BookStatus.AVAILABLE).bookGenre("thriller")
                    .bookAuthor("john doe").bookISBN("asdb234").publishedYear(1980)
                    .bookName("adventures of timmy").
                    build();


            User admin =

                    User.builder().firstName("admin").lastName("user").
                            email(ADMIN_EMAIL).password(passwordEncoder.encode(ADMIN_PASSWORD)).
                            userRole(UserRole.ADMIN).userEnabled(true).build();

            bookRepo.save(someBook);
            userRepo.save(admin);
        };
    }
}
