package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Book entities.
 * Provides standard SQL operations (CRUD) automatically.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    // Standard methods like save(), findAll(), deleteById() are inherited automatically
}
