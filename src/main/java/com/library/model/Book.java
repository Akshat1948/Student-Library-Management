package com.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

/**
 * Represents a Book in the library.
 * Demonstrates Encapsulation and Polymorphism (overriding toString and equals).
 * Now mapped as a JPA Entity for SQL persistence.
 */
@Entity
public class Book {
    @Id
    private String isbn;
    private String title;
    private String author;
    private String status; // "available" or "issued"
    private double price;

    // Default constructor required by JPA
    public Book() {}

    public Book(String isbn, String title, String author, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.status = "available";
        this.price = price;
    }

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("ISBN: %s | Title: %s | Author: %s | Status: %s | Price: $%.2f",
                isbn, title, author, status, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
