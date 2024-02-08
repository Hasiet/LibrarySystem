package Library;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Library {
    public static final String RESET = "\u001B[0m", GREEN = "\u001B[32m", YELLOW = "\u001B[33m";

    void borrowBook(Statement stmt, Scanner scanner){
        System.out.print("Enter user name: ");
        String user_name = scanner.nextLine().trim();
        try{
            String query = "SELECT COUNT(*) FROM user_table WHERE name = '" + user_name + "'";
            ResultSet rs = stmt.executeQuery(query);
            // Check the result
            rs.next();
            int count = rs.getInt(1);
            if(!user_name.isEmpty()){
                if (count > 0) {
                    System.out.print("Enter book name: ");
                    String book_name = scanner.nextLine().trim();
                    String query_book = "SELECT COUNT(*) FROM book_table WHERE name = '" + book_name + "'";
                    rs = stmt.executeQuery(query_book);
                    rs.next();
                    int countbook = rs.getInt(1);
                    if(!book_name.isEmpty()){
                        if (countbook > 0) {
                            // turn book name in to id, so we can inserd into user borrowed_book data.
                            String query_book_id = "SELECT id, quantity FROM book_table WHERE name = '" + book_name + "'";
                            rs= stmt.executeQuery(query_book_id);
                            if(rs.next()){
                                //inserting the found id.
                                int id = rs.getInt("id");
                                int quantity = rs.getInt("quantity");
                                if(quantity>0){
                                    boolean found = Check_borrowed_book(rs, stmt, user_name, id);
                                    if(found){
                                        System.out.println(YELLOW+"You already have the book!"+RESET);
                                    }
                                    else {
                                        String book_quantity = "UPDATE book_table SET quantity = quantity - 1 WHERE id = "+id;
                                        stmt.executeUpdate(book_quantity);
                                        String query_user_borrwed_book = "UPDATE user_table SET borrowed_book = array_append(borrowed_book, "+id+") WHERE name = '" + user_name +"'";
                                        int rowAffected = stmt.executeUpdate(query_user_borrwed_book);
                                        if (rowAffected>0){
                                            System.out.println(GREEN+"book is borrowed the book successfully!"+RESET);
                                        }else {
                                            System.out.println(YELLOW+"Something went wrong, try again!"+RESET);
                                        }
                                    }
                                }else{System.out.println(YELLOW+"Sorry, this book is out of stock, " +
                                            "plase come by again later!"+RESET);
                                }
                            }
                        }
                        else {System.out.println(YELLOW+"Book with name '"
                                + book_name + "' does not exist."+RESET);}
                    }
                } else {System.out.println(YELLOW+"User with name '"
                        + user_name + "' does not exist."+RESET);}
            }
            else {System.out.println(YELLOW + "USER doesn't exist." + RESET);}
        }catch(SQLException e){System.out.println(e);}
    }

    void returnBook(Statement stmt, Scanner scanner){
        System.out.print("Enter user name: ");
        String user_name = scanner.nextLine().trim();
        try{
            String query = "SELECT COUNT(*) FROM user_table WHERE name = '" + user_name + "'";
            ResultSet rs = stmt.executeQuery(query);
            // Check the result
            rs.next();
            int count = rs.getInt(1);
            if(!user_name.isEmpty()){
                if (count > 0) {
                    System.out.print("Enter book name: ");
                    String book_name = scanner.nextLine().trim();
                    String query_book = "SELECT COUNT(*) FROM book_table WHERE name = '" + book_name + "'";
                    rs = stmt.executeQuery(query_book);
                    rs.next();
                    int countbook = rs.getInt(1);
                    if(!book_name.isEmpty()){
                        if (countbook > 0) {
                            // turn book name in to id, so we can inserd into user borrowed_book data.
                            String query_book_id = "SELECT id FROM book_table WHERE name = '" + book_name + "'";
                            rs= stmt.executeQuery(query_book_id);
                            rs.next();
                            //inserting the found id.
                            int id = rs.getInt("id");
                            boolean found = Check_borrowed_book(rs, stmt, user_name, id);
                                if(found){

                                    String book_quantity = "UPDATE book_table SET quantity = quantity + 1 WHERE id = "+id;
                                    stmt.executeUpdate(book_quantity);
                                    String query_user_borrwed_book = "UPDATE user_table SET borrowed_book = array_remove(borrowed_book, "+id+") WHERE name = '" + user_name +"'";
                                    int rowAffected = stmt.executeUpdate(query_user_borrwed_book);
                                    if (rowAffected>0){
                                        System.out.println(GREEN+"book is returned successfully!"+RESET);
                                    }else {
                                        System.out.println(YELLOW+"Something went wrong, try again!"+RESET);
                                    }
                                }
                                else if (!found) {
                                    System.out.println(YELLOW+"You don't have the book you want to return"+RESET);
                                }

                        } else {
                            System.out.println(YELLOW+"Book with name '" + book_name + "' does not exist."+RESET);
                        }
                    }
                    else {
                        System.out.println(YELLOW + "Book input is empty!" + RESET);
                    }
                } else {
                    System.out.println(YELLOW+"User with name '" + user_name + "' does not exist."+RESET);
                }
            }
            else {
                System.out.println(YELLOW + "USER doesn't exist." + RESET);
            }
        }catch(SQLException e){System.out.println(e);}
    }

    private static boolean Check_borrowed_book(ResultSet rs,Statement stmt, String user_name, int id) throws SQLException{
        String query_borrowed_book = "SELECT borrowed_book FROM user_table WHERE name='"+ user_name +"'";
        rs = stmt.executeQuery(query_borrowed_book);
        // Move the cursor to the next row
        rs.next();
        // Get the borrowed_book array from the result set
        Array borrowedBooksArray = rs.getArray(1);
        boolean found = false;
        if (borrowedBooksArray != null) {
            // Retrieve the array of borrowed books
            Integer[] borrowedBooks = (Integer[]) borrowedBooksArray.getArray();
            // Check if the user has the book
            for (int bb : borrowedBooks) {
                if (bb == id) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
}
