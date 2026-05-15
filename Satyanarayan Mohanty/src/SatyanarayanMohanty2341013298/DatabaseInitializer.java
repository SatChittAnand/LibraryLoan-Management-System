package SatyanarayanMohanty2341013298;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {

        Connection conn = null;
        Statement stmt = null;

        try {

            conn = ConnectionManager.getConnection();

            stmt = conn.createStatement();

            // =========================
            // CREATE MEMBERS TABLE
            // =========================

            String createMembersTable =
                    "CREATE TABLE Members ("
                            + "MemberID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                            + "Name VARCHAR(100),"
                            + "Email VARCHAR(100) UNIQUE,"
                            + "ActiveLoans INT DEFAULT 0"
                            + ")";

            try {

                stmt.executeUpdate(createMembersTable);

                System.out.println(
                        "Members Table Created Successfully...");

            }

            catch (SQLException e) {

                System.out.println(
                        "Members Table Already Exists...");
            }

            // =========================
            // CREATE BOOKS TABLE
            // =========================

            String createBooksTable =
                    "CREATE TABLE Books ("
                            + "BookID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                            + "Title VARCHAR(200),"
                            + "Author VARCHAR(100),"
                            + "ISBN VARCHAR(30) UNIQUE,"
                            + "Available BOOLEAN DEFAULT TRUE"
                            + ")";

            try {

                stmt.executeUpdate(createBooksTable);

                System.out.println(
                        "Books Table Created Successfully...");

            }

            catch (SQLException e) {

                System.out.println(
                        "Books Table Already Exists...");
            }

            // =========================
            // CREATE LOANS TABLE
            // =========================

            String createLoansTable =
                    "CREATE TABLE Loans ("
                            + "LoanID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                            + "MemberID INT,"
                            + "BookID INT,"
                            + "LoanDate DATE,"
                            + "ReturnDate DATE,"
                            + "FOREIGN KEY (MemberID) "
                            + "REFERENCES Members(MemberID),"
                            + "FOREIGN KEY (BookID) "
                            + "REFERENCES Books(BookID)"
                            + ")";

            try {

                stmt.executeUpdate(createLoansTable);

                System.out.println(
                        "Loans Table Created Successfully...");

            }

            catch (SQLException e) {

                System.out.println(
                        "Loans Table Already Exists...");
            }

            // =========================
            // CREATE INDEXES
            // =========================

            try {

                stmt.executeUpdate(
                        "CREATE INDEX idx_isbn "
                                + "ON Books(ISBN)");

                System.out.println(
                        "ISBN Index Created...");

            }

            catch (SQLException e) {

                System.out.println(
                        "ISBN Index Already Exists...");
            }

            try {

                stmt.executeUpdate(
                        "CREATE INDEX idx_member "
                                + "ON Loans(MemberID)");

                System.out.println(
                        "MemberID Index Created...");

            }

            catch (SQLException e) {

                System.out.println(
                        "MemberID Index Already Exists...");
            }

            try {

                stmt.executeUpdate(
                        "CREATE INDEX idx_return "
                                + "ON Loans(ReturnDate)");

                System.out.println(
                        "ReturnDate Index Created...");

            }

            catch (SQLException e) {

                System.out.println(
                        "ReturnDate Index Already Exists...");
            }

            // =========================
            // INSERT SAMPLE MEMBERS
            // =========================

            String insertMember =
                    "INSERT INTO Members "
                            + "(Name, Email, ActiveLoans) "
                            + "VALUES (?, ?, ?)";

            PreparedStatement psMember =
                    conn.prepareStatement(insertMember);

            psMember.setString(1, "Rahul Sharma");
            psMember.setString(2, "rahul@gmail.com");
            psMember.setInt(3, 0);
            psMember.executeUpdate();

            psMember.setString(1, "Priya Das");
            psMember.setString(2, "priya@gmail.com");
            psMember.setInt(3, 0);
            psMember.executeUpdate();

            // =========================
            // INSERT SAMPLE BOOKS
            // =========================

            String insertBook =
                    "INSERT INTO Books "
                            + "(Title, Author, ISBN, Available) "
                            + "VALUES (?, ?, ?, ?)";

            PreparedStatement psBook =
                    conn.prepareStatement(insertBook);

            psBook.setString(1, "Java Programming");
            psBook.setString(2, "James Gosling");
            psBook.setString(3, "ISBN101");
            psBook.setBoolean(4, true);
            psBook.executeUpdate();

            psBook.setString(1, "Database Systems");
            psBook.setString(2, "C.J. Date");
            psBook.setString(3, "ISBN102");
            psBook.setBoolean(4, true);
            psBook.executeUpdate();

            System.out.println(
                    "\nSample Records Inserted Successfully...");

            // =========================
            // VERIFY INDEXES
            // =========================

            DatabaseMetaData metaData =
                    conn.getMetaData();

            ResultSet rs =
                    metaData.getIndexInfo(
                            null,
                            null,
                            "BOOKS",
                            false,
                            false);

            System.out.println(
                    "\nIndexes Present In BOOKS Table:");

            while (rs.next()) {

                System.out.println(
                        rs.getString("INDEX_NAME"));
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