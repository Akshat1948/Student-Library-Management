package com.library.datastructure;

import com.library.model.Book;

/**
 * A custom Singly Linked List implementation for managing Book inventory.
 * Does not use any built-in Java collection frameworks.
 */
public class CustomLinkedList {
    
    // Step 1: Inner Node class
    private static class Node {
        Book data;
        Node next;

        Node(Book data) {
            this.data = data;
            this.next = null;
        }
    }

    // Step 2: List properties
    private Node head;
    private Node tail;
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    // Step 3: Insertion Operations
    
    /**
     * Adds a book to the beginning of the list. O(1)
     */
    public void addFirst(Book book) {
        Node newNode = new Node(book);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    /**
     * Adds a book to the end of the list. O(1) because we maintain a tail pointer.
     */
    public void addLast(Book book) {
        Node newNode = new Node(book);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Inserts a book at a specific index. O(n)
     */
    public void insertAt(int index, Book book) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == 0) {
            addFirst(book);
        } else if (index == size) {
            addLast(book);
        } else {
            Node newNode = new Node(book);
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
            size++;
        }
    }

    // Step 4: Deletion Operations

    /**
     * Removes a book by its ISBN. O(n)
     */
    public boolean removeByIsbn(String isbn) {
        if (isEmpty()) return false;

        if (head.data.getIsbn().equals(isbn)) {
            head = head.next;
            if (head == null) tail = null;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null && !current.next.data.getIsbn().equals(isbn)) {
            current = current.next;
        }

        if (current.next != null) {
            if (current.next == tail) {
                tail = current;
            }
            current.next = current.next.next;
            size--;
            return true;
        }

        return false;
    }

    /**
     * Removes a book at a specific index. O(n)
     */
    public Book removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Book removedData;
        if (index == 0) {
            removedData = head.data;
            head = head.next;
            if (head == null) tail = null;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedData = current.next.data;
            if (current.next == tail) {
                tail = current;
            }
            current.next = current.next.next;
        }
        size--;
        return removedData;
    }

    /**
     * Display all books in the list.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    // These will be used in Phase 3 for Search and Sort
    
    /**
     * Searches for a book by ISBN using Linear Search. O(n)
     */
    public Book searchByIsbn(String isbn) {
        Node current = head;
        while (current != null) {
            if (current.data.getIsbn().equalsIgnoreCase(isbn)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Searches for a book by title using Linear Search. O(n)
     */
    public Book searchByTitle(String title) {
        Node current = head;
        while (current != null) {
            if (current.data.getTitle().equalsIgnoreCase(title)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Sorts the list by ISBN using Bubble Sort.
     * Swaps payloads (Book objects) instead of node pointers for safety. O(n^2)
     */
    public void bubbleSortByIsbn() {
        if (size <= 1) return;

        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.next != null) {
                if (current.data.getIsbn().compareToIgnoreCase(current.next.data.getIsbn()) > 0) {
                    // Swap data payloads
                    Book temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    /**
     * Converts the linked list to a standard Java List for API responses.
     */
    public java.util.List<Book> getAllBooks() {
        java.util.List<Book> books = new java.util.ArrayList<>();
        Node current = head;
        while (current != null) {
            books.add(current.data);
            current = current.next;
        }
        return books;
    }

    /**
     * Writes all books to a file using the provided BufferedWriter.
     */
    public void saveToFile(java.io.BufferedWriter writer) throws java.io.IOException {
        Node current = head;
        while (current != null) {
            Book b = current.data;
            String line = String.format("%s,%s,%s,%s,%.2f\n",
                    b.getIsbn(), b.getTitle(), b.getAuthor(), b.getStatus(), b.getPrice());
            writer.write(line);
            current = current.next;
        }
    }

    private Node getHead() { return head; }
}
