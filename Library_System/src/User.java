import java.util.ArrayList;
import java.util.Arrays;

public class User {
    String name;
    String group;
    int id  = (Library.users.size()-1) + 1;
    public void setUser(){
        ArrayList<String> user = new ArrayList<>(Arrays.asList(Integer.toString(this.id), this.name, this.group));
        Library.users.add(user);

    }
    public static void createUser(String name, String group){
        User user = new User();
        user.name = name;
        user.group = group;
        user.setUser();
    }
}
