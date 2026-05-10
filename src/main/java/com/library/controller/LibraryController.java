package com.library.controller;

import com.library.model.Book;
import com.library.service.LibraryManager;
import com.library.exception.BookNotAvailableException;
import com.library.exception.InvalidOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow requests from our React frontend
public class LibraryController {

    private final LibraryManager libraryManager;

    @Autowired
    public LibraryController(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return libraryManager.getAllBooks();
    }

    @PostMapping("/books")
    public void addBook(@RequestBody Book book) {
        libraryManager.addBook(book);
        libraryManager.saveToFile(); // Save persistence on every update
    }

    @DeleteMapping("/books/{isbn}")
    public void removeBook(@PathVariable String isbn) throws InvalidOperationException {
        libraryManager.removeBook(isbn);
        libraryManager.saveToFile();
    }

    @PostMapping("/borrow")
    public void borrowBook(@RequestBody Map<String, Object> payload) throws BookNotAvailableException, InvalidOperationException {
        String isbn = (String) payload.get("isbn");
        int studentId = (int) payload.get("studentId");
        libraryManager.borrowBook(isbn, studentId);
        libraryManager.saveToFile();
    }

    @PostMapping("/return/{isbn}")
    public void returnBook(@PathVariable String isbn) throws InvalidOperationException {
        libraryManager.returnBook(isbn);
        libraryManager.saveToFile();
    }

    @PostMapping("/sort")
    public void sortInventory() {
        libraryManager.sortInventory();
        libraryManager.saveToFile();
    }
}
