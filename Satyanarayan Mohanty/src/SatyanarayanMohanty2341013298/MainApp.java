package SatyanarayanMohanty2341013298;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice;

        System.out.println(
                "====================================");

        System.out.println(
                " LIBRARY LOAN MANAGEMENT SYSTEM ");

        System.out.println(
                "====================================");

        // Initialize Database
        DatabaseInitializer.initializeDatabase();

        do {

            System.out.println(
                    "\n========= MENU =========");

            System.out.println(
                    "1. Register Member");

            System.out.println(
                    "2. Add Book");

            System.out.println(
                    "3. View All Books");

            System.out.println(
                    "4. Process Loan");

            System.out.println(
                    "5. Return Book");

            System.out.println(
                    "6. View Active Loans");

            System.out.println(
                    "7. Search Book By ISBN");

            System.out.println(
                    "8. View Overdue Books");

            System.out.println(
                    "9. Run Performance Benchmark");

            System.out.println(
                    "10. Verify Tables");

            System.out.println(
                    "11. Exit");

            System.out.print(
                    "\nEnter Your Choice : ");

            choice = sc.nextInt();

            sc.nextLine();

            switch (choice) {

                // =========================
                // REGISTER MEMBER
                // =========================

                case 1:

                    System.out.print(
                            "\nEnter Member Name : ");

                    String memberName =
                            sc.nextLine();

                    System.out.print(
                            "Enter Email : ");

                    String email =
                            sc.nextLine();

                    BusinessLogic.registerMember(
                            memberName,
                            email);

                    break;

                // =========================
                // ADD BOOK
                // =========================

                case 2:

                    System.out.print(
                            "\nEnter Book Title : ");

                    String title =
                            sc.nextLine();

                    System.out.print(
                            "Enter Author Name : ");

                    String author =
                            sc.nextLine();

                    System.out.print(
                            "Enter ISBN : ");

                    String isbn =
                            sc.nextLine();

                    BusinessLogic.addBook(
                            title,
                            author,
                            isbn);

                    break;

                // =========================
                // VIEW BOOKS
                // =========================

                case 3:

                    BusinessLogic.viewBooks();

                    break;

                // =========================
                // PROCESS LOAN
                // =========================

                case 4:

                    System.out.print(
                            "\nEnter Member ID : ");

                    int memberId =
                            sc.nextInt();

                    System.out.print(
                            "Enter Book ID : ");

                    int bookId =
                            sc.nextInt();

                    TransactionService.processLoan(
                            memberId,
                            bookId);

                    break;

                // =========================
                // RETURN BOOK
                // =========================

                case 5:

                    System.out.print(
                            "\nEnter Member ID : ");

                    int returnMemberId =
                            sc.nextInt();

                    System.out.print(
                            "Enter Book ID : ");

                    int returnBookId =
                            sc.nextInt();

                    TransactionService.returnBook(
                            returnMemberId,
                            returnBookId);

                    break;

                // =========================
                // VIEW ACTIVE LOANS
                // =========================

                case 6:

                    BusinessLogic.viewActiveLoans();

                    break;

                // =========================
                // SEARCH BOOK
                // =========================

                case 7:

                    System.out.print(
                            "\nEnter ISBN : ");

                    String searchISBN =
                            sc.nextLine();

                    BusinessLogic.searchBookByISBN(
                            searchISBN);

                    break;

                // =========================
                // VIEW OVERDUE BOOKS
                // =========================

                case 8:

                    BusinessLogic.viewOverdueBooks();

                    break;

                // =========================
                // PERFORMANCE BENCHMARK
                // =========================

                case 9:

                    System.out.println(
                            "\n===== PERFORMANCE TESTS =====");

                    PerformanceEvaluator.warmUpDatabase();

                    PerformanceEvaluator
                            .enableRuntimeStatistics();

                    PerformanceEvaluator
                            .individualInsertTest(1000);

                    PerformanceEvaluator
                            .batchInsertTest(1000);

                    PerformanceEvaluator
                            .indexedSearchTest("ISBN101");

                    PerformanceEvaluator
                            .fullTableScanTest();

                    break;

                // =========================
                // VERIFY TABLES
                // =========================

                case 10:

                    ConnectionManager.verifyTables(
                            ConnectionManager.getConnection());

                    break;

                // =========================
                // EXIT
                // =========================

                case 11:

                    ConnectionManager.shutdownDatabase();

                    System.out.println(
                            "\nApplication Closed Successfully...");

                    break;

                default:

                    System.out.println(
                            "\nInvalid Choice...");
            }

        }

        while (choice != 11);

        sc.close();
    }
}