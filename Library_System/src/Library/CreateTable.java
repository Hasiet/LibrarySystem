package Library;

import java.sql.*;

public class CreateTable {
    void createUserTable(Connection con, Statement stmt) {
        try {
            if (!tableExists(con, "user_table")) {
                String sql = "CREATE TABLE user_table ("
                        + "id SERIAL PRIMARY KEY,"
                        + "name VARCHAR(255),"
                        + "group_name VARCHAR(255),"
                        + "borrowed_book INTEGER[])";
                stmt.executeUpdate(sql);
                System.out.println("User table created successfully!");
            } else {
                System.out.println("User table is active!");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    void createBookTable(Connection con, Statement stmt) {
        try {
            if (!tableExists(con, "book_table")) {
                String sql = "CREATE TABLE book_table ("
                        + "id SERIAL PRIMARY KEY,"
                        + "name VARCHAR(255),"
                        + "isbn INT,"
                        + "genre VARCHAR(255),"
                        + "author VARCHAR(255),"
                        + "year SMALLINT,"
                        + "quantity SMALLINT)";
                stmt.executeUpdate(sql);
                System.out.println("Book table created successfully!");
            } else {
                System.out.println("Book table is active!");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void createLibTable(Connection con, Statement stmt) {
        try {
            if (!tableExists(con, "lib_table")) {
                String sql = "CREATE TABLE lib_table ("
                        + "users INTEGER[],"
                        + "books INTEGER[])";
                stmt.executeUpdate(sql);
                System.out.println("Library table created successfully!");
            } else {
                System.out.println("library table is active!");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        return resultSet.next();
    }
}
