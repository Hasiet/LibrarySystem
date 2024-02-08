package Library;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Book{
    public static final String RESET = "\u001B[0m", RED = "\u001B[31m",GREEN = "\u001B[32m", YELLOW = "\u001B[33m";

    void setBook(Statement stmt, Scanner scanner){
        System.out.print("Title: ");
        String name = scanner.nextLine();
        System.out.print("Isbn: ");
        int isbn = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        if (name.isEmpty()||genre.isEmpty()||author.isEmpty()||quantity == 0){
            System.out.println(YELLOW+"The input is not correct, please try again!"+RESET);
        }
        else {
            try{
                String sql = "INSERT INTO book_table (name, isbn, genre, author, year, quantity) VALUES " +
                        "('" + name + "', " + isbn + ", '" + genre + "', '" + author + "', "+ year +", " + quantity + ")";
                int rowsAffected = stmt.executeUpdate(sql);
                if (rowsAffected > 0) {
                    System.out.println(GREEN+"Data inserted successfully into book_table."+RESET);
                } else {
                    System.out.println(RED+"Failed to insert data into book_table."+RESET);
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    void getBook(Statement stmt){
        try {
            String sql = "SELECT * FROM book_table";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int isbn = rs.getInt("isbn");
                String genre = rs.getString("genre");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                int quantity = rs.getInt("quantity");

                System.out.println("ID: " + id + "\nName: " + name + "\nISBN: " + isbn + "\nGenre: " + genre +
                        "\nAuthor: " + author + "\nYear: " + year + "\nQuantity: " + quantity + "\n");
            }
        } catch (Exception e) {System.out.println(e);}
    }
}
