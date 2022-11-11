package group6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *
 * @author 236351
 */
public class Student implements Serializable{
    
    static int MAX_SUBJECTS_NUMBER = 4;

    // Fields
    private int studentID;
    private String name;
    private String email;
    private String password;
    private List<Subject> subjects;

//    public Student() {}
    
    public Student(int ID, String name, String email, String password) {
        this.studentID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    // look up function to search subjects list by ID
    private Subject subject(int ID) {
        return subjects.stream().filter(p -> p.match(ID)).findAny().orElse(null);
    }

    // updated look up function to search subjects list by ID and return a list
    private List<Subject> subjects(int ID) {
        List<Subject> temp = new ArrayList();

        subjects.forEach(s -> {
            if (subject(ID) != null) {
                temp.add(s);
            }
        });

        return temp;
    }

    // Generates unique subjectID
    private int uniqueSubjectID() {
        int ID = Util.generatRand(1, 999);

        while (subject(ID) != null) {
            return Util.generatRand(1, 999);
        }

        return ID;
    }

    // Checks subject list maximum capacity
    public boolean subjectMaxcapacity() {
        return subjects.size() < MAX_SUBJECTS_NUMBER;
    }

    // Enrol subjects
    public void enrolSubject() {
        subjects.add(new Subject(uniqueSubjectID()));
    }

    // Drops subjects  ----- Ask or ID in controller -----
    private void dropSubject(int ID) {
        List<Subject> toDelete = subjects(ID);
        if (toDelete.size() > 0) {
            subjects.removeAll(toDelete);
            System.out.println(Util.YELLOW_BOLD+"Droping Subject-"+ID+Util.WHITE_BOLD);
            System.out.println(Util.YELLOW_BOLD+"You are now enrolled in "+subjects.size()
                                +" out of "+MAX_SUBJECTS_NUMBER+" subjects"+Util.WHITE_BOLD);
        }else{
            System.out.println(Util.RED_BOLD+"Subject-"+ID+" is not in your enrolment list"+Util.WHITE_BOLD);
        }
    }

    // Calculates the average mark of all subjects
    private double averageMark(){
        return  subjects.stream().mapToInt(s -> s.getMark()).average().getAsDouble();
    }
    
    // Checks if the student fail or pass based on the average mark
    private boolean passed(){
        return averageMark() >= 50;
    }
    
    // Changes student password    
    public void setPassword(String password) {
        this.password = password;
    }
    
    // Search for student by ID
    public boolean matchID(int ID){
        return this.studentID == ID;
    }
    
//    // Search for student by email
//    public boolean matchEmail(String email){
//        return this.email.equals(email);
//    }
//    
//    // Search for student by password
//    public boolean matchPassword(String password){
//        return this.password.equals(password);
//    }
    
    @Override
    public String toString() {
        return String.format("%-20s :: %06d --> Email: %s", name, studentID, email);
    }

//    public static void main(String[] args) {
//        Student s = new Student(10, "John Adamsassf", "john.adams@university.com", "Helloworld123456");
//        Student d = new Student(15320, "Aleana Rohdes", "john.rhodessmih@university.com", "Helloworld123456");
//        System.out.println(s);
//        System.out.println(d);
//    }
}
