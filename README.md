
# Library Loan Management System

A console-based JDBC mini project developed using Java and Apache Derby Database.  
This project demonstrates transaction management, JDBC performance evaluation, ACID properties, indexing, benchmarking, and database operations using Derby Embedded Database.

---

# Features

- Member Registration
- Add Books
- Process Book Loans
- Return Books
- Active Loan Tracking
- Overdue Book Detection
- ISBN-Based Search
- Transaction Management
- Savepoints & Rollbacks
- Benchmark Performance Evaluation
- Indexed Query Optimization
- Runtime Statistics
- Derby Database Integration

---

# Technologies Used

- Java
- JDBC
- Apache Derby Database
- Eclipse IDE

---

# Project Structure

```text
src/
 └── SatyanarayanMohanty2341013298/
      ├── MainApp.java
      ├── ConnectionManager.java
      ├── DatabaseInitializer.java
      ├── TransactionService.java
      ├── BusinessLogic.java
      ├── PerformanceEvaluator.java
```

---

# Database Features

* Embedded Derby Database
* Explicit Transaction Handling
* Commit & Rollback
* Savepoints
* Indexed Search
* Runtime Statistics
* Metadata Verification

---

# JDBC Concepts Implemented

* PreparedStatement
* Batch Processing
* Transaction Management
* DatabaseMetaData
* Savepoints
* Exception Handling
* Resource Cleanup
* Performance Benchmarking

---

# Benchmark Tests

The project compares:

* Individual Insert vs Batch Insert
* Indexed Search vs Full Table Scan
* Transaction Performance
* Query Execution Time
* Throughput Analysis

---

# Derby Configuration

Database URL:

```java
jdbc:derby:lab10db;create=true
```

Shutdown URL:

```java
jdbc:derby:lab10db;shutdown=true
```

---

# Required Library

Add the following JAR file to Eclipse Build Path:

```text
derby.jar
```

---

# How To Run

## Step 1

Import project into Eclipse.

## Step 2

Add `derby.jar` to Build Path.

## Step 3

Run:

```text
MainApp.java
```

---

# Sample Menu

```text
1. Register Member
2. Add Book
3. View All Books
4. Process Loan
5. Return Book
6. View Active Loans
7. Search Book By ISBN
8. View Overdue Books
9. Run Performance Benchmark
10. Verify Tables
11. Exit
```
---
# Workflow Diagram

```mermaid
graph TD

A[Start Application] --> B[Initialize Derby Database]

B --> C[Create Tables]
C --> D[Insert Sample Data]

D --> E[Display Main Menu]

E --> F1[Register Member]
E --> F2[Add Book]
E --> F3[View Books]
E --> F4[Process Loan]
E --> F5[Return Book]
E --> F6[View Active Loans]
E --> F7[Search Book By ISBN]
E --> F8[View Overdue Books]
E --> F9[Run Performance Benchmark]
E --> F10[Verify Tables]
E --> F11[Exit Application]

F4 --> G[Check Book Availability]

G --> H{Book Available?}

H -->|Yes| I[Update Book Status]
I --> J[Insert Loan Record]
J --> K[Update Member Loan Count]
K --> L[Commit Transaction]

H -->|No| M[Rollback Transaction]

F5 --> N[Update Return Status]
N --> O[Update Loan Record]
O --> P[Commit Return Transaction]

F9 --> Q[Warm Up Database]
Q --> R[Enable Runtime Statistics]
R --> S[Run Individual Insert Test]
S --> T[Run Batch Insert Test]
T --> U[Run Indexed Search Test]
U --> V[Run Full Table Scan Test]

F11 --> W[Shutdown Derby Database]
W --> X[End Application]
```

---

# Performance Optimization

* Warm-up phase before benchmarks
* Indexed lookup optimization
* Runtime statistics enabled
* Derby metadata verification

---

# Learning Outcomes

This project demonstrates:

* JDBC Programming
* ACID Transactions
* Database Benchmarking
* Derby Database Management
* Exception Handling
* Query Optimization

---

# Author

Satyanarayan Mohanty

---

# License

This project is licensed under the MIT License.

```
```
