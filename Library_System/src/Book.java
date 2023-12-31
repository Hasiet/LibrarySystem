import java.util.ArrayList;
import java.util.List;

public class Book {
    List<String> bookData = new ArrayList<>();

    Book(String title, long isbn, String genre, String author, int year, int quantity){
        bookData.add(title);
        bookData.add(Long.toString(isbn));
        bookData.add(genre);
        bookData.add(author);
        bookData.add(Integer.toString(year));
        bookData.add(Integer.toString(quantity));
        boolean sameBook = false;
        for(int i=0; i<Library.books.size(); i++){
            if (bookData.equals(Library.books.get(i))){
                sameBook = true;
                List<String> innerList = Library.books.get(i);
                if(Integer.parseInt(innerList.get(5)) <= 0){
                    innerList.set(5, "0");
                }
                else {
                    innerList.set(5, Integer.toString(Integer.parseInt(innerList.get(5))+quantity));
                }
                break;
            }

        }
        if (!sameBook){
            Library.books.add(bookData);
        }
    }


}
