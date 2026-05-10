# Manual Test Cases for Library Management System

| Test Case | Step | Expected Result |
| :--- | :--- | :--- |
| **1. Add Book** | Choice 1 -> ISBN: 101, Title: Java Basics, Author: John Doe, Price: 29.99 | "Book added successfully." |
| **2. Search (ISBN)** | Choice 2 -> Choice 1 -> Query: 101 | Should display: ISBN: 101 | Title: Java Basics... |
| **3. Search (Title)** | Choice 2 -> Choice 2 -> Query: Java Basics | Should display: ISBN: 101 | Title: Java Basics... |
| **4. Borrow Book** | Choice 3 -> ISBN: 101, Student ID: 500 | "Book borrowed successfully." |
| **5. Availability Check** | Choice 5 (Display All) | Status for ISBN 101 should be "issued". |
| **6. Duplicate Borrow** | Choice 3 -> ISBN: 101, Student ID: 501 | "Error: Book is already issued." |
| **7. Return Book** | Choice 4 -> ISBN: 101 | "Book returned successfully." |
| **8. Sorting** | Add 103, then 102. Choice 6 (Sort) | Choice 5 should show 101, 102, 103 in order. |
| **9. Persistence** | Choice 7 (Save), then Choice 8 (Exit). Restart app. | Choice 5 should still show all previously added books. |
| **10. Invalid Input** | Enter "abc" at Main Menu | "Invalid input. Please enter a number." |
