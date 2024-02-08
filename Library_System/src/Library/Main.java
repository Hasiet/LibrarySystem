package Library;
import java.util.Scanner;
import java.sql.*;

public class Main {
    public static final String RESET = "\u001B[0m", RED = "\u001B[31m", PURPLE = "\u001B[35m", GREEN = "\u001B[32m";
    static String menu = PURPLE +"1)Add book;\n" +
            "2)Show all available books;\n" +
            "3)Add a new user;\n" +
            "4)Borrow book;\n" +
            "5)Return book;\n" +
            "6)Users & Borrowed books;\n" +
            GREEN+"Pick the number to execute the menu;\n"+"write \"menu\" to see the menu;\n"+ "\"quit\" to exit;" + RESET;


    //Connect to Database
    static String conlink = "jdbc:postgresql://localhost:5432/MainDB";
    static Connection con = null;
    static Statement stmt = null;
    //Making connection and stmt as accessible for everywhere
    static {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conlink, "postgres", "hasiet.00");
            stmt = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        //Create Table
        CreateTable Table = new CreateTable();
        try {
            Class.forName("org.postgresql.Driver");
            Table.createUserTable(con, stmt);
            Table.createBookTable(con, stmt);
            Table.createLibTable(con, stmt);
        }catch (Exception e){System.out.println(e);}

        //terminal commands
        System.out.println(menu);
        boolean run = true;
        while (run) {
            System.out.print("==>: ");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();
            scanner.nextLine();
            try {
                if(option.toLowerCase().trim().equals("menu")||option.toLowerCase().trim().equals("help")){
                    System.out.println(menu);
                }
                else if(option.toLowerCase().trim().equals("quit")){
                    run = false;
                }
                else{

                    //switch-case to excecute the desired command
                    Book book = new Book();
                    User user = new User();
                    Library lib = new Library();
                    switch (Integer.parseInt(option)){
                        //Add Book
                        case 1: {book.setBook(stmt, scanner);break;}
                        //View Book
                        case 2: {book.getBook(stmt);break;}
                        //Add User
                        case 3: {user.setUser(stmt, scanner);break;}
                        //View User
                        case 4: {user.getUser(stmt);break;}
                        //Borrow Book
                        case 5: {lib.borrowBook(stmt, scanner);break;}
                        //Rerurn Book
                        case 6: {lib.returnBook(stmt, scanner);break;}
                        default: System.out.println(RED + "Option not found, try again!" + RESET);
                    }
                }
            }catch (Exception e) {System.out.println(RED + "Looks like there is a mistake, try Again!!" + RESET);}
        }
    }
}