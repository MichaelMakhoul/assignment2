package group6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 236351
 */
public class Student implements Serializable {

    static int MAX_SUBJECTS_NUMBER = 4;

    // Fields
    private int studentID;
    private String name;
    private String email;
    private String password;
    private List<Subject> subjects = new ArrayList();

    // Constructor 
    public Student(int ID, String name, String email, String password) {
        this.studentID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Accessors 
    public int getStudentID() {
        return studentID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    // Mutators 
    // Changes student password    
    public void setPassword(String password) {
        this.password = password;
    }

    // look up function to search subjects list by ID
    private Subject subject(int ID) {
        return subjects.stream().filter(p -> p.match(ID)).findAny().orElse(null);
    }

    // updated look up function to search subjects list by ID and return a list
    private List<Subject> subjects(int ID) {
        List<Subject> temp = new ArrayList();

        for (Subject subject : subjects) {
            if (subject.getSubjectID() == ID) {
                temp.add(subject);
            }
        }

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
    public boolean checkMaxCapacity() {
        return subjects.size() < MAX_SUBJECTS_NUMBER;
    }

    // Get number of enrolled subjects
    private int numberOfSubjects() {
        return subjects.size();
    }

    // Enrol subjects
    public void enrolSubject(int ID) {
        Subject subject = new Subject(ID);
        subjects.add(subject);
        System.out.println(Util.YELLOW_BOLD + subject.getName() + Util.WHITE_BOLD);
        System.out.println(Util.YELLOW_BOLD + "You are now enrolled in "
                           +numberOfSubjects()+ " out of " +MAX_SUBJECTS_NUMBER
                           + " subjects" + Util.WHITE_BOLD);
    }

    public void enrolSubject() {
        enrolSubject(uniqueSubjectID());
    }

    // Drops subjects
    public void dropSubject(int ID) {
        List<Subject> toDelete = new ArrayList();
        
        toDelete.addAll(subjects(ID));
        
        if (toDelete.size() > 0) {
            subjects.removeAll(toDelete);
            System.out.println(Util.YELLOW_BOLD + "Droping Subject-" + ID + Util.WHITE_BOLD);
            System.out.println(Util.YELLOW_BOLD + "You are now enrolled in " + subjects.size()
                    + " out of " + MAX_SUBJECTS_NUMBER + " subjects" + Util.WHITE_BOLD);
        } else {
            System.out.println(Util.RED_BOLD + "Subject-" + ID + " is not in your enrolment list" + Util.WHITE_BOLD);
        }
    }

    // Calculates the average mark of all subjects
    private double averageMark() {
        return subjects.stream().mapToInt(s -> s.getMark()).average().getAsDouble();
    }

    // Checks if the student fail or pass based on the average mark
    private boolean passed() {
        return averageMark() >= 50;
    }

    // Search for student by ID
    public boolean matchID(int ID) {
        return this.studentID == ID;
    }

    public void showSubjects() {
        System.out.println(Util.YELLOW_BOLD+"Showing "+numberOfSubjects()+" subjects"+Util.WHITE_BOLD);
        subjects.forEach(System.out::println);
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
        return String.format("%-20s :: %06d --> Email: %s", name, studentID, email + "  " +subjects + "  " +password);
    }
}
