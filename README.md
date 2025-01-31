# ☕ Java OOP Mastery - Web Applications & Design Patterns

*A collection of object-oriented web applications demonstrating Java Servlets, MVC architecture, and clean code practices*

---

## 🌐 Overview
This repository showcases my journey through **Object-Oriented Programming** fundamentals and **web application development** using Java EE. These projects emphasize SOLID principles, design patterns, and modern web architecture while building functional server-side applications.

**Tech Stack**  
![Java](https://img.shields.io/badge/Java-11%2B-orange?logo=openjdk)
![Servlets](https://img.shields.io/badge/Servlets-4.0-important)
![Web](https://img.shields.io/badge/Web-HTML5%20|%20CSS3%20|%20JS-yellowgreen)
![Database](https://img.shields.io/badge/Database-JDBC%20|%20MySQL-blue)

---

## 🏗️ Projects

### [E-Commerce Platform](/online-bookstore)
- **MVC Architecture**: Separated presentation, business, and data layers
- **Key Features**:
  - Session management with cookies
  - JDBC connection pooling
  - Product catalog with OOP inheritance (`Book` ➔ `EBook`/`PhysicalBook`)
- **Tech**: Java Servlets, JSP, Bootstrap 5

### [Blog Engine](/blog-platform)
- **DAO Pattern**: Abstracted database operations
- **OOP Concepts**:
  - Polymorphic comment system (`Comment` ➔ `UserComment`/`AdminComment`)
  - Factory method for content types
- **Tech**: JSTL, MySQL, Connection pooling

### [Task Manager](/task-system)
- **SOLID Implementation**:
  - Strategy pattern for task prioritization
  - Observer pattern for email notifications
- **Web Design**: Responsive UI with CSS Grid
- **Tech**: Java Mail API, JQuery, AJAX

---

## 🧩 OOP Concepts Demonstrated

```java
// Abstraction example
public abstract class PaymentProcessor {
    public abstract void processPayment(double amount);
}

// Encapsulation example
public class User {
    private String password; 
    public void setPassword(String hashed) { /* validation */ }
}

// Polymorphism example
List<Shape> shapes = new ArrayList<>();
shapes.add(new Circle());
shapes.add(new Square());
shapes.forEach(Shape::draw);
