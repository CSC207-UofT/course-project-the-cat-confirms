import java.util.List;
import java.util.ArrayList;

public class Account {
    private final String utorid;
    private String password;
    private final List<Object> course;
    /*private List<T> course;

     */

    private Account(String utorid, String password){
        this.utorid = utorid;
        this.password = password;
        this.course = new ArrayList<>();
    }

    public String getUtorid() {
        return utorid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public List<Object> getCourse() {
        return course;
    }

    public void addToCourse(Object object){this.course.add(object);
    }

    public static void main(String[] args) {
        Account x = new Account("zhaoyi61", "123456"
        );
        System.out.println(x.getUtorid());
        System.out.println(x.getPassword());
        System.out.println(x.getCourse());
        x.addToCourse("CSC148");
        System.out.println(x.getCourse());
        x.setPassword("654321");
        System.out.println(x.getPassword());
    }
}

