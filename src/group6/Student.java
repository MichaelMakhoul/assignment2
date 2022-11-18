package group6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* The Student Class contains data about students including their ID, name, email and password.
* 
* @author Group-6
*/
public class Student implements Serializable {

    /**
     * Maximum number of allowed subjects to enroll in
     */
    static int MAX_SUBJECTS_NUMBER = 4;

    /**
     * Student Fields.
     */
    private int studentID;
    private String name;
    private String email;
    private String password;
    private List<Subject> subjects = new ArrayList();

    /**
     * Student Class Constructor.
     */ 
    public Student(int ID, String name, String email, String password) {
        this.studentID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Accessors 
    /**
     * Gives access to StudentID.
     * @return studentID
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Gives access to name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gives access to email.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gives access to password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    // Mutators    
    /**
     * Updates student password.
     * @param password - students password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Look up function to search subjects list by ID.
     * @param ID - StudentID
     * @return Subject
     */
    private Subject subject(int ID) {
        return subjects.stream().filter(p -> p.match(ID)).findAny().orElse(null);
    }

    /**
     * Updated look up function to search subjects list by ID and return a list.
     * @param ID - StudentID
     * @return list of subjects
     */
    private List<Subject> subjects(int ID) {
        List<Subject> temp = new ArrayList();

        for (Subject subject : subjects) {
            if (subject.getSubjectID() == ID) {
                temp.add(subject);
            }
        }

        return temp;
    }

    /**
     * Generates unique subjectID.
     * @return unique ID
     */
    private int uniqueSubjectID() {
        int ID = Util.generatRand(1, 999);

        while (subject(ID) != null) {
            return Util.generatRand(1, 999);
        }

        return ID;
    }

    /**
     * Checks subject list maximum capacity.
     * @return true or false
     */
    public boolean checkMaxCapacity() {
        return subjects.size() < MAX_SUBJECTS_NUMBER;
    }

    /**
     * Check if the student has enrolled to at least one subject.
     * @return true or false
     */
    public boolean hasEnrolled() {
        return subjects.size() > 0;
    }

    /**
     * Get number of enrolled subjects.
     * @return number of enrolled subjects
     */
    private int numberOfSubjects() {
        return subjects.size();
    }

    /**
     * Adds new subject to subjects list.
     * @param ID - Subject ID
     */
    public void enrolSubject(int ID) {
        Subject subject = new Subject(ID);
        subjects.add(subject);
        System.out.println(Util.YELLOW_BOLD + "\t\tEnrolling in " 
                           + subject.getName() + Util.WHITE_BOLD);
        
        System.out.println(Util.YELLOW_BOLD + "\t\tYou are now enrolled in "
                           + numberOfSubjects() + " out of " + MAX_SUBJECTS_NUMBER
                           + " subjects" + Util.WHITE_BOLD);
    }
    
    /**
     * Runs enrolSubject function
     */
    public void enrolSubject() {
        enrolSubject(uniqueSubjectID());
    }

    /**
     * Removes a subject from subjects list.
     * @param ID - Subject ID
     */
    public void dropSubject(int ID) {
        List<Subject> toDelete = new ArrayList();

        toDelete.addAll(subjects(ID));

        if (toDelete.size() > 0) {
            subjects.removeAll(toDelete);
            System.out.println(Util.YELLOW_BOLD + "\t\tDroping Subject-" + ID + Util.WHITE_BOLD);
            System.out.println(Util.YELLOW_BOLD + "\t\tYou are now enrolled in " + subjects.size()
                    + " out of " + MAX_SUBJECTS_NUMBER + " subjects" + Util.WHITE_BOLD);
        } else {
            System.out.println(Util.RED_BOLD + "\t\tSubject-" + ID + " is not in your enrolment list" + Util.WHITE_BOLD);
        }
    }

    /**
     * Calculates the average mark of all subjects.
     * @return average mark
     */
    public double averageMark() {
        return subjects.stream().mapToInt(s -> s.getMark()).average().getAsDouble();
    }
    
    /**
     * Determines the grade based on the mark 
     * @return Student's final grade
     */
    public String getGrade() {
        return Util.grade(averageMark());
    }

    /**
     * Checks if the student fail or pass based on the average mark.
     * @return true or false
     */
    public boolean passed() {
        return averageMark() >= 50;
    }

    /**
     * Search for student by ID.
     * @param ID
     * @return 
     */
    public boolean matchID(int ID) {
        return this.studentID == ID;
    }

    /**
     * Prints out the list of subjects.
     */
    public void showSubjects() {
        System.out.println(Util.YELLOW_BOLD + "\t\tShowing " + numberOfSubjects() + " subjects" + Util.WHITE_BOLD);
        subjects.forEach(s -> System.out.println("\t\t"+s));
    }

    /**
     * Search for student by email.
     * @param email - Students email
     * @return true or false
     */
    public boolean matchEmail(String email){
        return this.email.equals(email);
    }
 
    /**
     * Returns the name, ID, grade and average mark for each student as a string.
     * @param format - The maximum length of students name.
     * @return String format
     */
    public String printDetails(int format){
        return String.format("%-"+format+"s :: %06d --> GRADE: %2s - MARK: %.2f", name, studentID, getGrade() ,averageMark());
    }
    
    /**
     * Overrides the toString function.
     * @return String format.
     */
    @Override
    public String toString() {
        int format = Util.maxNameLength();
        return String.format("%-"+format+"s :: %06d --> Email: %s", name, studentID, email);
    }
}
