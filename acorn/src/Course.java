import org.codehaus.groovy.ast.stmt.ContinueStatement;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private final String courseName;
    private final int courseTerm;
    private final String courseId;
    private int numberOfAccount;
    private final List<Account> accountInCourse;

    private Course(String courseName, int courseTerm,
                   String courseId){
        this.courseName = courseName;
        this.courseTerm = courseTerm;
        this.courseId = courseId;
        this.numberOfAccount = 0;
        this.accountInCourse = new ArrayList<>();
        }

    public int getCourseTerm() {
        return courseTerm;
    }

    public ArrayList<String> getAccountInCourse() {
        ArrayList<String> name = new ArrayList<>();
        for (Account i : accountInCourse) {
            name.add(i.getUtorid());
        }
        return name;
    }

    public int getNumberOfAccount() {
        return numberOfAccount;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void addAccount(Account newStudent){
        if (accountInCourse.contains(newStudent)) {
            System.out.println("student already in courses");
        }
        else {accountInCourse.add(newStudent);
        numberOfAccount ++;
        newStudent.addToCourse(new Course(this.courseName,this.courseTerm,this.courseId));
        }
    }

    public static void main(String[] args) {
        Account studentA = new Account("Peter","123457");
        Course x = new Course("csc207",3,"1");
        System.out.println(x.getNumberOfAccount());
        System.out.println(x.getAccountInCourse());
        x.addAccount(studentA);
        System.out.println(x.getAccountInCourse());
        System.out.println(x.getNumberOfAccount());
        System.out.println(studentA.getCourse());
        x.addAccount(studentA);
        System.out.println(studentA.getCourse());

    }

}
