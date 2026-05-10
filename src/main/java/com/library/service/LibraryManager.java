package com.library.service;

import com.library.datastructure.CustomLinkedList;
import com.library.exception.BookNotAvailableException;
import com.library.exception.InvalidOperationException;
import com.library.model.Book;
import com.library.model.Transaction;
import com.library.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class that manages library operations.
 * Now refactored to use SQL Database (H2) for persistence.
 */
@Service
public class LibraryManager {
    private final CustomLinkedList inventory;
    private final List<Transaction> transactions;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryManager(BookRepository bookRepository) {
        this.inventory = new CustomLinkedList();
        this.transactions = new ArrayList<>();
        this.bookRepository = bookRepository;
    }

    /**
     * Loads all books from the SQL database into the custom linked list on startup.
     */
    @PostConstruct
    public void init() {
        List<Book> booksFromDb = bookRepository.findAll();
        for (Book book : booksFromDb) {
            inventory.addLast(book);
        }
        System.out.println("Data loaded from SQL Database successfully.");
    }

    public void addBook(Book book) {
        inventory.addLast(book);
        bookRepository.save(book); // Save to SQL
    }

    public void removeBook(String isbn) throws InvalidOperationException {
        boolean removed = inventory.removeByIsbn(isbn);
        if (!removed) {
            throw new InvalidOperationException("Book with ISBN " + isbn + " not found.");
        }
        bookRepository.deleteById(isbn); // Remove from SQL
    }

    public Book findBook(String query, boolean byIsbn) {
        if (byIsbn) {
            return inventory.searchByIsbn(query);
        } else {
            return inventory.searchByTitle(query);
        }
    }

    public void borrowBook(String isbn, int studentId) throws BookNotAvailableException, InvalidOperationException {
        Book book = inventory.searchByIsbn(isbn);
        if (book == null) {
            throw new InvalidOperationException("Book not found.");
        }
        if (!book.getStatus().equals("available")) {
            throw new BookNotAvailableException("Book is already issued.");
        }
        book.setStatus("issued");
        bookRepository.save(book); // Update status in SQL
        transactions.add(new Transaction(isbn, studentId));
    }

    public void returnBook(String isbn) throws InvalidOperationException {
        Book book = inventory.searchByIsbn(isbn);
        if (book == null) {
            throw new InvalidOperationException("Book not found.");
        }
        if (book.getStatus().equals("available")) {
            throw new InvalidOperationException("Book was not issued.");
        }
        book.setStatus("available");
        bookRepository.save(book); // Update status in SQL
        transactions.removeIf(t -> t.getIsbn().equals(isbn));
    }

    public List<Book> getAllBooks() {
        return inventory.getAllBooks();
    }

    public void sortInventory() {
        inventory.bubbleSortByIsbn();
        // Sorting doesn't change SQL state, only in-memory list order
    }

    public void displayInventory() {
        inventory.display();
    }

    /**
     * Manual save is no longer needed with JPA, 
     * but we keep the method signature for compatibility.
     */
    public void saveToFile() {
        // No action needed: individual methods handle SQL updates
        System.out.println("SQL Database is synchronized.");
    }
}
