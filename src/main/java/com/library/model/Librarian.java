package com.library.model;

/**
 * Derived class representing a Librarian.
 * Inherits from User.
 */
public class Librarian extends User {
    public Librarian(int id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Librarian [" + super.toString() + "]";
    }
}
