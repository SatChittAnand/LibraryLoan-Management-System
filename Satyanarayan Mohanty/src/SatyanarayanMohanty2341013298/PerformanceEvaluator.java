package SatyanarayanMohanty2341013298;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PerformanceEvaluator {

    // =========================
    // WARM-UP METHOD
    // =========================

    public static void warmUpDatabase() {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            Statement stmt =
                    conn.createStatement();

            // Warm-up Queries
            for (int i = 0; i < 100; i++) {

                stmt.executeQuery(
                        "SELECT * FROM Books");
            }

            System.out.println(
                    "\nDatabase Warm-Up Completed...");
        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // ENABLE RUNTIME STATISTICS
    // =========================

    public static void enableRuntimeStatistics() {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            Statement stmt =
                    conn.createStatement();

            stmt.execute(
                    "CALL SYSCS_UTIL.SYSCS_SET_RUNTIMESTATISTICS(1)");

            System.out.println(
                    "\nRuntime Statistics Enabled...");
        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // INDIVIDUAL INSERT TEST
    // =========================

    public static void individualInsertTest(
            int recordCount) {

        Connection conn = null;

        long startTime;
        long endTime;

        try {

            conn =
                    ConnectionManager.getConnection();

            String query =
                    "INSERT INTO Books "
                            + "(Title, Author, ISBN, Available) "
                            + "VALUES (?, ?, ?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            startTime =
                    System.nanoTime();

            for (int i = 1; i <= recordCount; i++) {

                ps.setString(
                        1,
                        "Book" + i);

                ps.setString(
                        2,
                        "Author" + i);

                ps.setString(
                        3,
                        "ISBN" + i);

                ps.setBoolean(
                        4,
                        true);

                ps.executeUpdate();
            }

            endTime =
                    System.nanoTime();

            double timeMs =
                    (endTime - startTime)
                            / 1000000.0;

            System.out.println(
                    "\n===== INDIVIDUAL INSERT TEST =====");

            System.out.println(
                    "Records Inserted : "
                            + recordCount);

            System.out.println(
                    "Execution Time : "
                            + timeMs
                            + " ms");

            System.out.println(
                    "Throughput : "
                            + (recordCount / (timeMs / 1000))
                            + " ops/sec");
        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // BATCH INSERT TEST
    // =========================

    public static void batchInsertTest(
            int recordCount) {

        Connection conn = null;

        long startTime;
        long endTime;

        try {

            conn =
                    ConnectionManager.getConnection();

            conn.setAutoCommit(false);

            String query =
                    "INSERT INTO Books "
                            + "(Title, Author, ISBN, Available) "
                            + "VALUES (?, ?, ?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            startTime =
                    System.nanoTime();

            for (int i = 1; i <= recordCount; i++) {

                ps.setString(
                        1,
                        "BatchBook" + i);

                ps.setString(
                        2,
                        "BatchAuthor" + i);

                ps.setString(
                        3,
                        "BISBN" + i);

                ps.setBoolean(
                        4,
                        true);

                ps.addBatch();
            }

            ps.executeBatch();

            conn.commit();

            endTime =
                    System.nanoTime();

            double timeMs =
                    (endTime - startTime)
                            / 1000000.0;

            System.out.println(
                    "\n===== BATCH INSERT TEST =====");

            System.out.println(
                    "Records Inserted : "
                            + recordCount);

            System.out.println(
                    "Execution Time : "
                            + timeMs
                            + " ms");

            System.out.println(
                    "Throughput : "
                            + (recordCount / (timeMs / 1000))
                            + " ops/sec");
        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // INDEXED SEARCH TEST
    // =========================

    public static void indexedSearchTest(
            String isbn) {

        Connection conn = null;

        long startTime;
        long endTime;

        try {

            conn =
                    ConnectionManager.getConnection();

            String query =
                    "SELECT * FROM Books "
                            + "WHERE ISBN = ?";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setString(1, isbn);

            startTime =
                    System.nanoTime();

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                rs.getString("Title");
            }

            endTime =
                    System.nanoTime();

            double timeMs =
                    (endTime - startTime)
                            / 1000000.0;

            System.out.println(
                    "\n===== INDEXED SEARCH TEST =====");

            System.out.println(
                    "Execution Time : "
                            + timeMs
                            + " ms");
        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }

    // =========================
    // FULL TABLE SCAN TEST
    // =========================

    public static void fullTableScanTest() {

        Connection conn = null;

        long startTime;
        long endTime;

        try {

            conn =
                    ConnectionManager.getConnection();

            Statement stmt =
                    conn.createStatement();

            startTime =
                    System.nanoTime();

            ResultSet rs =
                    stmt.executeQuery(
                            "SELECT * FROM Books");

            while (rs.next()) {

                rs.getString("Title");
            }

            endTime =
                    System.nanoTime();

            double timeMs =
                    (endTime - startTime)
                            / 1000000.0;

            System.out.println(
                    "\n===== FULL TABLE SCAN TEST =====");

            System.out.println(
                    "Execution Time : "
                            + timeMs
                            + " ms");
        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        finally {

            ConnectionManager.closeConnection(conn);
        }
    }
}