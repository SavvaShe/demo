package DB;

import Settings.ClusterStore.ClusterOne;

import java.sql.*;

public class Connect {

        static final String DB_URL = "jdbc:postgresql://localhost:5432/ARM";
        static final String USER = "SITH";
        static final String PASS = "pechenki";



    //  Database credentials
public static ClusterOne executeSelect(String query) throws SQLException { //Для select
    try {
        Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
        System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
        e.printStackTrace();
        return null;
    }

    //System.out.println("PostgreSQL JDBC Driver successfully connected");
    Connection connection = null;

    try {
        connection = DriverManager
                .getConnection(DB_URL, USER, PASS);

    } catch (SQLException e) {
        System.out.println("Connection Failed");
        e.printStackTrace();
        return null;
    }

    ResultSet rs = null;
    if (connection != null) {
        Statement selectStmt = connection.createStatement();
        rs = selectStmt
                .executeQuery(query);
    } else {
        System.out.println("Failed to make connection to database");
    }
    return (ClusterOne) rs;
}
   public static void  executeDDL(String query) throws SQLException {// Для Insert, Update, Delete

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        //System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } else {
            System.out.println("Failed to make connection to database");
        }
    }
        }


