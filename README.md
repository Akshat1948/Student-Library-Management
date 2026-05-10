# 📚 Student Library Management System

A full-stack, responsive web application for managing library operations, built with **Java (Spring Boot)**, **React**, and **SQL**. This project demonstrates advanced software engineering principles by implementing custom data structures and algorithms from scratch rather than relying solely on built-in collections.

## 🚀 Features

- **Custom Data Structures:** Implements a custom **Singly Linked List** for in-memory book management.
- **Advanced Algorithms:** Features a custom **Bubble Sort** implementation (O(n²)) and **Linear Search** (O(n)).
- **Full-Stack Architecture:** 
  - **Backend:** Spring Boot REST API.
  - **Frontend:** Responsive React + TypeScript UI (Mobile & Desktop friendly).
- **Persistent Storage:** Integrated with **H2 SQL Database** via Spring Data JPA.
- **Business Logic:** Support for adding books, searching inventory, borrowing (assigning to Student IDs), and returning books.
- **Robustness:** Includes custom exception handling and input validation.

## 🛠️ Tech Stack

- **Backend:** Java 21, Spring Boot 3.x, Spring Data JPA, H2 Database, Maven.
- **Frontend:** React 19, TypeScript, Vite, Vanilla CSS.
- **Tools:** Git, Maven Wrapper.

## 📋 Prerequisites

Before running the project, ensure you have the following installed:
- **Java 21 (JDK)**
- **Node.js** (v18 or higher)
- **npm**

## 💻 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/Akshat1948/Student-Library-Management.git
cd Student-Library-Management
```

### 2. Run the Application
I have provided an automation script to start both the backend and frontend simultaneously:

```bash
chmod +x start_app.sh
./start_app.sh
```

- **Frontend:** Open [http://localhost:5173](http://localhost:5173) in your browser.
- **Backend API:** Access [http://localhost:8080/api/books](http://localhost:8080/api/books).
- **Database Console:** View the SQL tables at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:file:./library_db`).

## 🧪 Testing the System

1. **Add a Book:** Enter the ISBN, Title, Author, and Price.
2. **Borrow:** Enter a **Student ID** in the controls section, then click **Borrow** on any available book.
3. **Return:** Click the **Return** button on any issued book to make it available again.
4. **Sort:** Click **Sort Inventory** to see your books rearrange themselves alphabetically by ISBN using the Bubble Sort algorithm.

## 📖 Key OOP Concepts Applied

- **Encapsulation:** Private fields and public getters/setters in domain models.
- **Inheritance:** `Student` and `Librarian` classes extending the base `User` class.
- **Polymorphism:** Method overriding for `toString()` and `equals()` in the `Book` entity.
- **Abstraction:** Hiding complex pointer manipulation within the `CustomLinkedList` implementation.

---
Developed as a comprehensive exercise in Full-Stack Java Engineering.
