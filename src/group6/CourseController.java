package group6;

import java.util.ArrayList;
import java.util.List;

/**
* The StudentController Class, Helps Students to manage their accounts and review their marks.
* 
* @author Group-6
*/

public class CourseController {
    
    /**
     * An instance of Student Class - used to manage a students data in students list.
     */
    Student student;
    
    /**
     * List of students, an instance of Student Class - used to hold
     * students data from the database.
     */
    private List<Student> students = new ArrayList();
    
    /**
     * CourseController Class Constructor.
     * @param ID - Students ID
     */
    public CourseController(int ID) {
        initList();
        this.student = setSession(ID);
        menu();   
    }
    
    /**
    * Initialize students list with data from students database.
    */
    public void initList(){
        Database db = new Database();
        students = db.readStudents();
    }
    
    /**
     * Starts a user session after they login using their StudentID
     * @param ID - Student ID
     * @return Student
     */
    private Student setSession(int ID){
        return students.stream().filter(s -> s.getStudentID() == ID).findAny().orElse(null);
    }
    
    /**
     * Updates students.data file after modifying the list.
     * @param s - The signed in Student
     */
    private void updateList(Student s){
        int position = students.indexOf(s);
        students.set(position, student);
        Util.updateFile(students);
    }
    
    /**
     * Adds a new subject to the students database using enrolSubject function from Student Class.
     */
    private void enrol(){
        if(student.checkMaxCapacity()){
            student.enrolSubject();
            updateList(student);
        }else{
            System.out.println(Util.RED_BOLD+"\t\tStudents are allowed to enrol in 4 subjects only"+Util.WHITE_BOLD);
        }
    }
    
    /**
     * Checks if the email and password matches the correct format.
     * @param password - Users password
     * @return password with correct format
     */
    private String checkPasswordFormate(String password){        
        while(!Util.passwordRegex(password)){
            System.out.println(Util.RED_BOLD+"\t\tIncorrect password format"+Util.WHITE_BOLD);
            password = Util.readString("\t\tNew Password: ");
        }

        return password;
    }
    
    /**
     * Checks if the new password matches confirm password - used when users update their password.
     * @param password - new password
     * @param confirm - confirm password
     * @return true or false
     */
    private boolean passwordMatch(String password, String confirm){        
        boolean matches;
        
        while(!(matches = password.equals(confirm))){
            System.out.println(Util.RED_BOLD+"\t\tPassword does not match - try again"+Util.WHITE_BOLD);
            confirm = Util.readString("\t\tConfirm Password: ");
        }
        
        return matches;
    }
    
    /**
     * Reads in confirm password and updates students password in the database, 
     * after checking if it matches the new entered password.
     * @param newPassword - new password
     */
    private void confirmPassword(String newPassword){
        String confirmPassword;
        
        if(passwordMatch(newPassword, confirmPassword = Util.readString("\t\tConfirm Password: "))){
            student.setPassword(newPassword);
            updateList(student);
        }
    }
    
    /**
     * Reads in new password and uses checkPasswordFormate and confirmPassword functions.
     */
    private void change(){
        String newPassword = checkPasswordFormate(Util.readString("\t\tNew Password: "));
        confirmPassword(newPassword);
    }
    
    /**
     * Removes a subject by ID.
     */
    private void remove(){
        int ID = Util.readNumber("\t\tRemove Subject by ID: ");
        student.dropSubject(ID);
        updateList(student);
    }
    
    /**
     * Prints out the list of subjects that the student enrolled in. 
     */
    private void show(){
        student.showSubjects();
    }
    
    /**
     * Student Course System Menu - Enables students to manage their account. 
     */
    private void menu() {
        char c;
        while ((c = Util.readChoice("\t\tStudent Course Menu(c/e/r/s/x): ")) != 'x') {
            switch (c) {
                case 'c':
                    change();
                    break;
                case 'e':
                    enrol();
                    break;
                case 'r':
                    remove();
                    break;
                case 's':
                    show();
                    break;    
                default:
                    help();
                    break;
            }
        }
    }

    /**
     * Displays the available options for the user to choose from.
     */
    private void help() {
        System.out.println("\t\tc - change");
        System.out.println("\t\te - enrol");
        System.out.println("\t\tr - remove");
        System.out.println("\t\ts - show");
        System.out.println("\t\tx - exit");
    }
}
