package com.LibManagementSystem.LibManagementSystem.repo;


import com.LibManagementSystem.LibManagementSystem.models.BookRelated.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
}
