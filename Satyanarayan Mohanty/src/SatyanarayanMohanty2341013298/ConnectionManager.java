package SatyanarayanMohanty2341013298;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionManager {

    // Embedded Derby URL
    private static final String URL =
            "jdbc:derby:lab10db;create=true";

    public static Connection getConnection() {

        Connection conn = null;

        try {

            // Load Embedded Driver
            Class.forName(
                    "org.apache.derby.jdbc.EmbeddedDriver");

            // Establish Connection
            conn =
                    DriverManager.getConnection(URL);

            System.out.println(
                    "\nConnected Successfully...");

            DatabaseMetaData metaData =
                    conn.getMetaData();

            System.out.println(
                    "Database : "
                            + metaData.getDatabaseProductName());

        }

        catch (ClassNotFoundException e) {

            System.out.println(
                    "Driver Not Found...");
            e.printStackTrace();
        }

        catch (SQLException e) {

            System.out.println(
                    "Connection Failed...");
            e.printStackTrace();
        }

        return conn;
    }

    // Verify Tables
    public static void verifyTables(
            Connection conn) {

        try {

            DatabaseMetaData metaData =
                    conn.getMetaData();

            ResultSet rs =
                    metaData.getTables(
                            null,
                            null,
                            "%",
                            new String[]{"TABLE"});

            System.out.println(
                    "\nAvailable Tables:");

            while (rs.next()) {

                System.out.println(
                        rs.getString("TABLE_NAME"));
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // Close Connection
    public static void closeConnection(
            Connection conn) {

        try {

            if (conn != null &&
                    !conn.isClosed()) {

                conn.close();

                System.out.println(
                        "\nConnection Closed...");
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // Shutdown Derby
    public static void shutdownDatabase() {

        try {

            DriverManager.getConnection(
                    "jdbc:derby:lab10db;shutdown=true");

        }

        catch (SQLException e) {

            System.out.println(
                    "\nDerby Shutdown Successfully...");
        }
    }
}