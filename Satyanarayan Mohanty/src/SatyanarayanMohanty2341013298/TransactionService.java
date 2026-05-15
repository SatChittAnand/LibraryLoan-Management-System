package SatyanarayanMohanty2341013298;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.sql.SQLException;

public class TransactionService {

    // =========================
    // PROCESS BOOK LOAN
    // =========================

    public static void processLoan(
            int memberId,
            int bookId) {

        Connection conn = null;

        Savepoint savepoint = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            // Disable Auto Commit
            conn.setAutoCommit(false);

            // =========================
            // CHECK BOOK AVAILABILITY
            // =========================

            String checkBook =
                    "SELECT Available "
                            + "FROM Books "
                            + "WHERE BookID = ?";

            PreparedStatement psCheck =
                    conn.prepareStatement(checkBook);

            psCheck.setInt(1, bookId);

            ResultSet rs =
                    psCheck.executeQuery();

            if (rs.next()) {

                boolean available =
                        rs.getBoolean("Available");

                if (!available) {

                    System.out.println(
                            "Book Not Available...");

                    conn.rollback();

                    return;
                }
            }

            else {

                System.out.println(
                        "Book Not Found...");

                conn.rollback();

                return;
            }

            // =========================
            // UPDATE BOOK STATUS
            // =========================

            String updateBook =
                    "UPDATE Books "
                            + "SET Available = FALSE "
                            + "WHERE BookID = ?";

            PreparedStatement psBook =
                    conn.prepareStatement(updateBook);

            psBook.setInt(1, bookId);

            psBook.executeUpdate();

            System.out.println(
                    "Book Status Updated...");

            // =========================
            // CREATE SAVEPOINT
            // =========================

            savepoint =
                    conn.setSavepoint(
                            "LoanInsertSavepoint");

            // =========================
            // INSERT LOAN RECORD
            // =========================

            String insertLoan =
                    "INSERT INTO Loans "
                            + "(MemberID, BookID, LoanDate, ReturnDate) "
                            + "VALUES (?, ?, CURRENT_DATE, NULL)";

            PreparedStatement psLoan =
                    conn.prepareStatement(insertLoan);

            psLoan.setInt(1, memberId);
            psLoan.setInt(2, bookId);

            psLoan.executeUpdate();

            System.out.println(
                    "Loan Record Inserted...");

            // =========================
            // UPDATE ACTIVE LOAN COUNT
            // =========================

            String updateMember =
                    "UPDATE Members "
                            + "SET ActiveLoans = ActiveLoans + 1 "
                            + "WHERE MemberID = ?";

            PreparedStatement psMember =
                    conn.prepareStatement(updateMember);

            psMember.setInt(1, memberId);

            int rows =
                    psMember.executeUpdate();

            // Simulate Failure
            if (rows == 0) {

                System.out.println(
                        "Member Update Failed...");

                conn.rollback(savepoint);

                System.out.println(
                        "Rolled Back To Savepoint...");
            }

            else {

                System.out.println(
                        "Member Loan Count Updated...");
            }

            // =========================
            // COMMIT TRANSACTION
            // =========================

            conn.commit();

            System.out.println(
                    "\nTransaction Committed Successfully...");
        }

        catch (SQLException e) {

            try {

                if (conn != null) {

                    conn.rollback();

                    System.out.println(
                            "\nTransaction Rolled Back...");
                }

            }

            catch (SQLException ex) {

                ex.printStackTrace();
            }

            e.printStackTrace();
        }

        finally {

            try {

                if (conn != null) {

                    conn.setAutoCommit(true);
                }

            }

            catch (SQLException e) {

                e.printStackTrace();
            }

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // RETURN BOOK
    // =========================

    public static void returnBook(
            int memberId,
            int bookId) {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            conn.setAutoCommit(false);

            // =========================
            // UPDATE BOOK STATUS
            // =========================

            String updateBook =
                    "UPDATE Books "
                            + "SET Available = TRUE "
                            + "WHERE BookID = ?";

            PreparedStatement psBook =
                    conn.prepareStatement(updateBook);

            psBook.setInt(1, bookId);

            psBook.executeUpdate();

            // =========================
            // UPDATE RETURN DATE
            // =========================

            String updateLoan =
                    "UPDATE Loans "
                            + "SET ReturnDate = CURRENT_DATE "
                            + "WHERE MemberID = ? "
                            + "AND BookID = ? "
                            + "AND ReturnDate IS NULL";

            PreparedStatement psLoan =
                    conn.prepareStatement(updateLoan);

            psLoan.setInt(1, memberId);
            psLoan.setInt(2, bookId);

            psLoan.executeUpdate();

            // =========================
            // UPDATE MEMBER LOAN COUNT
            // =========================

            String updateMember =
                    "UPDATE Members "
                            + "SET ActiveLoans = ActiveLoans - 1 "
                            + "WHERE MemberID = ?";

            PreparedStatement psMember =
                    conn.prepareStatement(updateMember);

            psMember.setInt(1, memberId);

            psMember.executeUpdate();

            // =========================
            // COMMIT
            // =========================

            conn.commit();

            System.out.println(
                    "\nBook Returned Successfully...");
        }

        catch (SQLException e) {

            try {

                if (conn != null) {

                    conn.rollback();

                    System.out.println(
                            "\nReturn Transaction Failed...");
                }

            }

            catch (SQLException ex) {

                ex.printStackTrace();
            }

            e.printStackTrace();
        }

        finally {

            try {

                if (conn != null) {

                    conn.setAutoCommit(true);
                }

            }

            catch (SQLException e) {

                e.printStackTrace();
            }

            ConnectionManager.closeConnection(conn);
        }
    }
}