package Library;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class User {
    public static String RESET = "\u001B[0m", RED = "\u001B[31m", GREEN = "\u001B[32m", YELLOW = "\u001B[33m";

    void setUser(Statement stmt, Scanner scanner){
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Group: ");
        String group = scanner.nextLine().trim();
         if (name.isEmpty() || group.isEmpty()) {
            System.out.println(YELLOW + "No name or group is entered, try again!" + RESET);
        } else {
            try{
                String sql = "INSERT INTO user_table (name, group_name) VALUES " +
                        "('" + name + "', '" + group + "')";
                int rowsAffected = stmt.executeUpdate(sql);
                if (rowsAffected > 0) {
                    System.out.println(GREEN+"Data inserted successfully into user_table."+RESET);
                } else {
                    System.out.println(RED+"Failed to insert data into user_table."+RESET);
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    void getUser(Statement stmt){
        try {
            String sql = "SELECT * FROM user_table";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String group = rs.getString("group_name");
                Array borrowed_book_array = rs.getArray("borrowed_book");

                //check if array is empty
                Integer[] borrowedBooks = null;
                if (borrowed_book_array != null) {
                    borrowedBooks = (Integer[]) borrowed_book_array.getArray();
                }

                //print data
                System.out.println("ID: " + id + "\nName: " + name + "\nGroup: " + group);
                System.out.print("Borrowed Books: ");

                //If list is not empty
                if (borrowedBooks != null) {
                    System.out.println(Id_to_Value(borrowedBooks, stmt));
                } else {
                    System.out.println();
                }
                System.out.println();
            }
        } catch (Exception e) {System.out.println(e);}
    }

    private String Id_to_Value(Integer[] book_ids, Statement stmt) throws Exception {
        StringBuilder nameBuilder = new StringBuilder();

        // Use a separate Statement object for executing queries
        try (Statement innerStmt = stmt.getConnection().createStatement()) {
            for (int id : book_ids){
                String query = "SELECT name FROM book_table WHERE id = " + id;
                ResultSet rs = innerStmt.executeQuery(query);
                if (rs.next()) {
                    String bookName = rs.getString("name");
                    nameBuilder.append(bookName).append(", ");
                }
                rs.close(); // Close the ResultSet after each iteration
            }
        }

        // Remove the trailing comma and space
        String name = nameBuilder.toString().trim();
        if (name.endsWith(",")) {
            name = name.substring(0, name.length() - 1);
        }

        return name;
    }
}
