package SatyanarayanMohanty2341013298;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BusinessLogic {

    // =========================
    // REGISTER MEMBER
    // =========================

    public static void registerMember(
            String name,
            String email) {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            String query =
                    "INSERT INTO Members "
                            + "(Name, Email, ActiveLoans) "
                            + "VALUES (?, ?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, 0);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "\nMember Registered Successfully...");
            }

        }

        catch (SQLException e) {

            System.out.println(
                    "\nError Registering Member...");
            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // ADD BOOK
    // =========================

    public static void addBook(
            String title,
            String author,
            String isbn) {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            String query =
                    "INSERT INTO Books "
                            + "(Title, Author, ISBN, Available) "
                            + "VALUES (?, ?, ?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, isbn);
            ps.setBoolean(4, true);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "\nBook Added Successfully...");
            }

        }

        catch (SQLException e) {

            System.out.println(
                    "\nError Adding Book...");
            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // VIEW ALL BOOKS
    // =========================

    public static void viewBooks() {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            String query =
                    "SELECT * FROM Books";

            Statement stmt =
                    conn.createStatement();

            ResultSet rs =
                    stmt.executeQuery(query);

            System.out.println(
                    "\n========== BOOK LIST ==========");

            while (rs.next()) {

                System.out.println(
                        "\nBook ID : "
                                + rs.getInt("BookID"));

                System.out.println(
                        "Title : "
                                + rs.getString("Title"));

                System.out.println(
                        "Author : "
                                + rs.getString("Author"));

                System.out.println(
                        "ISBN : "
                                + rs.getString("ISBN"));

                System.out.println(
                        "Available : "
                                + rs.getBoolean("Available"));
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // VIEW ACTIVE LOANS
    // =========================

    public static void viewActiveLoans() {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            String query =
                    "SELECT LoanID, MemberID, BookID, LoanDate "
                            + "FROM Loans "
                            + "WHERE ReturnDate IS NULL";

            Statement stmt =
                    conn.createStatement();

            ResultSet rs =
                    stmt.executeQuery(query);

            System.out.println(
                    "\n====== ACTIVE LOANS ======");

            while (rs.next()) {

                System.out.println(
                        "\nLoan ID : "
                                + rs.getInt("LoanID"));

                System.out.println(
                        "Member ID : "
                                + rs.getInt("MemberID"));

                System.out.println(
                        "Book ID : "
                                + rs.getInt("BookID"));

                System.out.println(
                        "Loan Date : "
                                + rs.getDate("LoanDate"));
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // SEARCH BOOK BY ISBN
    // =========================

    public static void searchBookByISBN(
            String isbn) {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            String query =
                    "SELECT * FROM Books "
                            + "WHERE ISBN = ?";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setString(1, isbn);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                System.out.println(
                        "\nBook Found:");

                System.out.println(
                        "Book ID : "
                                + rs.getInt("BookID"));

                System.out.println(
                        "Title : "
                                + rs.getString("Title"));

                System.out.println(
                        "Author : "
                                + rs.getString("Author"));

                System.out.println(
                        "Available : "
                                + rs.getBoolean("Available"));
            }

            else {

                System.out.println(
                        "\nBook Not Found...");
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // VIEW OVERDUE BOOKS
    // =========================

    public static void viewOverdueBooks() {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            String query =
            		"SELECT * FROM Loans "
            		+ "WHERE ReturnDate IS NULL "
            		+ "AND LoanDate < CURRENT_DATE - 15";

            Statement stmt =
                    conn.createStatement();

            ResultSet rs =
                    stmt.executeQuery(query);

            System.out.println(
                    "\n====== OVERDUE BOOKS ======");

            while (rs.next()) {

                System.out.println(
                        "\nLoan ID : "
                                + rs.getInt("LoanID"));

                System.out.println(
                        "Member ID : "
                                + rs.getInt("MemberID"));

                System.out.println(
                        "Book ID : "
                                + rs.getInt("BookID"));

                System.out.println(
                        "Loan Date : "
                                + rs.getDate("LoanDate"));
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }
}