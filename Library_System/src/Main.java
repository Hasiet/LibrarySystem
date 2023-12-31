import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String PURPLE = "\u001B[35m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static void main(String[] args) {
        String menu = PURPLE +"1)Add book;\n" +
                "2)Show all available books;\n" +
                "3)Add a new user;\n" +
                "4)Borrow book;\n" +
                "5)Return book;\n" +
                "6)Borrowed books & users;\n" +
                GREEN+"Pick the number to execute the menu;\n"+"write \"menu\" to see the menu;\n"+ "\"quit\" to exit;" + RESET;
        System.out.println(menu);
        boolean run = true;
        while (run) {
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();
            scanner.nextLine();
            try {
                if(option.toLowerCase().trim().equals("menu")){
                    System.out.println(menu);
                }
                else if(option.toLowerCase().trim().equals("quit")){
                    run = false;
                }
                else{
                    switch (Integer.parseInt(option)){
                        case 1:
                            System.out.print("Title: ");
                            String title = scanner.nextLine();
                            System.out.print("Isbn: ");
                            long isbn = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("Genre: ");
                            String genre = scanner.nextLine();
                            System.out.print("Author: ");
                            String author = scanner.nextLine().trim();
                            System.out.print("Year: ");
                            int year = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Quantity: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();
                            if (title.isEmpty()||genre.isEmpty()||author.isEmpty()){
                                System.out.println(YELLOW+"The input is not correct, please try again!"+RESET);
                            }
                            else {
                                Book newBook = new Book(title, isbn, genre, author, year, quantity);
                                System.out.println(GREEN+"Book created successfuly!"+RESET);
                            }
                            break;

                        case 2:
                            Library.getBooks();
                            break;

                        case 3:
                            System.out.print("Name: ");
                            String name = scanner.nextLine().trim();
                            System.out.print("Group: ");
                            String group = scanner.nextLine().trim();
                            if (name.isEmpty() ||group.isEmpty()){
                                System.out.println(YELLOW+"No name or group is entered, try again!"+RESET);
                            }else{
                                User.createUser(name, group);
                                System.out.println(GREEN+"User is created successfuly!"+RESET);
                            }
                            break;

                        case 4:
                            for(int i=0; i<Library.users.size(); i++){
                                List<String> innerList = Library.users.get(i);
                                System.out.println("User name: "+innerList.get(1));
                            }

                            System.out.print("Enter user name: ");
                            String userName = scanner.nextLine();
                            boolean existance = false;
                            boolean bookExistance = false;
                            for(int i=0; i<Library.users.size(); i++) {
                                List<String> innerUser = Library.users.get(i);
                                if(userName.trim().equals(innerUser.get(1))){
                                    existance = true;
                                    System.out.print("Enter book name: ");
                                    String bookName = scanner.nextLine().toLowerCase();
                                    for (int j =0; j<Library.books.size(); j++){
                                        List<String> innerBook = Library.books.get(j);
                                        if(bookName.equals(innerBook.get(0).toLowerCase())&&Integer.parseInt(innerBook.get(5)) != 0){
                                            bookExistance = true;
                                            Library.users.get(i).add(innerBook.get(0));
                                            innerBook.set(5, Integer.toString(Integer.parseInt(innerBook.get(5))-1));
                                            System.out.println(GREEN+"Borrowed successfuly!"+RESET);
                                        }
                                        else if(bookName.equals(innerBook.get(0).toLowerCase())&&Integer.parseInt(innerBook.get(5)) == 0){
                                            System.out.println(YELLOW+"The book has already been borrowed, come again later!"+RESET);
                                        }
                                    }
                                    if (!bookExistance){
                                        System.out.println(YELLOW+"There is no such a book in library."+RESET);
                                    }

                                }
                            }
                            if (!existance) {
                                System.out.println(YELLOW+"USER doesn't exist."+RESET);
                            }
                            break;

                        case 5:
                            for(int i=0; i<Library.users.size(); i++){
                                List<String> innerList = Library.users.get(i);
                                System.out.println("User name: "+innerList.get(1));
                            }

                            System.out.print("Enter user name: ");
                            String userNameReturn = scanner.nextLine();
                            boolean userExist = false;
                            for(int i=0; i<Library.users.size(); i++) {
                                List<String> innerUser = Library.users.get(i);
                                if(userNameReturn.trim().equals(innerUser.get(1))){
                                    userExist = true;
                                    boolean exist = false;
                                    if(innerUser.size() > 3){
                                        System.out.print("Enter book name: ");
                                        String bookName = scanner.nextLine().toLowerCase();
                                        for (int j =0; j<Library.books.size(); j++){
                                            List<String> innerBook = Library.books.get(j);
                                            if(bookName.equals(innerBook.get(0).toLowerCase())){
                                                for (int k = 3; k<innerUser.size(); k++){
                                                    if (bookName.equals(innerUser.get(k).toLowerCase())){
                                                        exist= true;
                                                        Library.users.get(i).remove(innerUser.get(k));
                                                        innerBook.set(5, Integer.toString(Integer.parseInt(innerBook.get(5))+1));
                                                        System.out.println(GREEN + "Returned successfuly!"+ RESET);
                                                    }
                                                }
                                            }
                                        }
                                        if(!exist){
                                            System.out.println(YELLOW+ "You dont have such a book!" +RESET);
                                        }
                                    }
                                    else {
                                        System.out.println(YELLOW+"You don't have any books to return!"+RESET);
                                    }
                                }
                            }
                            if (!userExist) {
                                System.out.println(YELLOW+"USER doesn't exist."+RESET);
                            }
                            break;

                        case 6:
                            Library.getInfo();
                            break;

                        default:
                            System.out.println(RED + "Option not found, try again!" + RESET);
                    }
                }
            }catch (Exception e) {
                System.out.println(RED + "Looks like there is a mistake, try Again!!" + RESET);
                continue;
            }
        }
    }
}