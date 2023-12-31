import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Library {
    static List<String> user1 = new ArrayList<>(Arrays.asList("0", "Amir", "A", "The Great Gatsby", "To Kill a Mockingbird"));
    static List<String> user2 = new ArrayList<>(Arrays.asList("1", "Isabel", "A", "The Lord of the Rings"));
    static List<String> user3 = new ArrayList<>(Arrays.asList("2", "Brian", "B"));
    static List<String> user4 = new ArrayList<>(Arrays.asList("3", "Amina", "B", "Looking for Alaska"));
    static List<List<String>> users = new ArrayList<>(Arrays.asList(user1, user2, user3, user4));

    static List<String> book1 = new ArrayList<>(Arrays.asList("The Great Gatsby", "9780333791035", "Novel", "F. Scott Fitzgerald", "1925", "5"));
    static List<String> book2 = new ArrayList<>(Arrays.asList("To Kill a Mockingbird", "9780475509739 ", "Fiction", "Harper Lee", "1970", "2"));
    static List<String> book3 = new ArrayList<>(Arrays.asList("Looking for Alaska", "9780211655000", "Coming Of Age", "John Green", "2005", "1"));
    static List<String> book4 = new ArrayList<>(Arrays.asList("The Lord of the Rings", "9780000123407", "Fantasy", "J.R.R. Tolkien", "1955", "8"));
    static List<List<String>> books = new ArrayList<>(Arrays.asList(book1, book2, book3,book4));

    static int sum(){
        int sum = 0;
        for (List<String> innerList : books) {
            sum += Integer.parseInt(innerList.get(5));
        }
        return sum;
    }

    public static void getBooks(){
        for(int i=0; i<Library.books.size(); i ++){
            List<String> innerList = Library.books.get(i);
            System.out.print("Book "+ (i+1) + ":\n");
            System.out.print("  Title: "+innerList.get(0) + "\n");
            System.out.print("  Isbn: "+innerList.get(1) + "\n");
            System.out.print("  Genre: "+innerList.get(2) + "\n");
            System.out.print("  Author: "+innerList.get(3) + "\n");
            System.out.print("  Year: "+innerList.get(4) + "\n");
            System.out.println("  Quantity: "+innerList.get(5) + "\n");
        }
        System.out.println("All the books in library: " + Library.sum());
    }

    public static void getInfo(){
        for(int i =0; i<Library.users.size(); i++){
            List<String> inner = Library.users.get(i);
            System.out.println("Id: " + inner.get(0));
            System.out.println("User name: "+inner.get(1));
            System.out.println("Group: "+inner.get(2));
            if(inner.size() > 3){

                System.out.print("Books: ");
                for (int j = 3; j<inner.size(); j++){
                    System.out.print(inner.get(j)+ "; ");
                }
                System.out.println();

            }
            else{
                System.out.println("Books: none");
            }
            System.out.println();
        }
    }

}
