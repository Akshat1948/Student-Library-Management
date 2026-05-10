package com.library.model;

import java.time.LocalDate;

/**
 * Represents a borrowing transaction.
 */
public class Transaction {
    private String isbn;
    private int studentId;
    private LocalDate borrowDate;

    public Transaction(String isbn, int studentId) {
        this.isbn = isbn;
        this.studentId = studentId;
        this.borrowDate = LocalDate.now();
    }

    public String getIsbn() { return isbn; }
    public int getStudentId() { return studentId; }
    public LocalDate getBorrowDate() { return borrowDate; }

    @Override
    public String toString() {
        return "Transaction [ISBN: " + isbn + ", Student ID: " + studentId + ", Date: " + borrowDate + "]";
    }
}
