package com.library.model;

/**
 * Derived class representing a Student.
 * Inherits from User and can be extended with student-specific properties.
 */
public class Student extends User {
    private int borrowedCount;

    public Student(int id, String name) {
        super(id, name);
        this.borrowedCount = 0;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    @Override
    public String toString() {
        return "Student [" + super.toString() + ", Borrowed: " + borrowedCount + "]";
    }
}
