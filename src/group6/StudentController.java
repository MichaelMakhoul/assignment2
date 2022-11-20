package group6;

import java.util.ArrayList;
import java.util.List;

/**
* The StudentController Class, Allows Students to login to their accounts or register new account.
* 
* @author Group-6
*/
public class StudentController {
    /**
     * List of students, an instance of Student Class - used to hold
     * students data from the database.
     */
    private List<Student> students = new ArrayList();

    /**
     * StudentController Class Constructor.
     */
    public StudentController() {
        initList();
        try {
            menu();   
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(Util.RED_BOLD+"\tUnknown command"+Util.WHITE_BOLD);
        }
    }
    
    /**
     * Initialize students list with data from students database.
     */
    public void initList(){
        Database db = new Database();
        students = db.readStudents();
    }
    
    /**
     * Checks if the student exists in the database.
     * @param email - students email
     * @param password - students password
     * @return A Student or null.
     */
    private Student existingStudent(String email, String password){
        return students.stream().
        filter(p -> (p.matchEmail(email) && p.getPassword().equals(password))).
        findAny().orElse(null);
    }
    
    /**
     * A look up function to search students list by email.
     * @param email - students email
     * @return true or false.
     */
    private boolean emailExists(String email) {
        boolean found = students.stream().anyMatch(p -> p.matchEmail(email));
        
        if (found){
            System.out.println(Util.RED_BOLD+"\t"+"Email already exists"+Util.WHITE_BOLD);
        }
        
        return found;
    }
    
    /**
     * Checks if the email and password matches the correct format.
     * @param email - students email
     * @param password - students password
     * @return true or false.
     */
    private boolean checkFormat(String email, String password){
        if(Util.emailRegex(email) && Util.passwordRegex(password)){
            System.out.println(Util.YELLOW_BOLD+"\temail and password formats acceptable"+Util.WHITE_BOLD);
            return true;
        } else {
            System.err.println(Util.RED_BOLD+"\tIncorrect email or password format"+Util.WHITE_BOLD); 
            return false;
        }
    }
    
    /**
     * Uses existingStudent function to check users credentials if they exist
     * and allows access to Course menu.
     * 
     * @param email - students email
     * @param password - students password
     * @return Student or null
     */
    private Student login(String email, String password){      
        initList();
        Student student = existingStudent(email, password);
        
        if(student != null){
            return student;             
        } else {
            System.out.println(Util.RED_BOLD+"\tStudent does not exist"+Util.WHITE_BOLD);
        }
        
        return null;
    }
    
    /**
     * Reads users email and password from STDIN and uses login function to authorize 
     * access to their account.
     */
    private void login(){
        String email;
        String password;
        
        System.out.println(Util.GREEN_BOLD+"\t"+"Student Sign In"+Util.WHITE_BOLD);
        while(!checkFormat(email = Util.readString("\tEmail: "), password = Util.readString("\tPassword: ")));
        
        Student student = login(email, password);
        
        if(student != null){
            new CourseController(student.getStudentID());
        }
    }
    
    /**
     * A look up function to search students list by ID.
     * @param ID - Students ID
     * @return Student or null
     */
    private Student student(int ID) {
        return students.stream().filter(p -> p.matchID(ID)).findAny().orElse(null);
    }
    
    /**
     * Generates unique subjectID.
     * @return unique ID.
     */
    private int uniqueStudentID() {
        int ID = Util.generatRand(1, 999999);

        while (student(ID) != null) {
            return Util.generatRand(1, 999999);
        }

        return ID;
    }
    
    /**
     * Adds new student to the students database.
     * @param email - students email
     * @param password - students password
     */
    private void register(String email, String password){
        String name = Util.readString("\tName: ");
        students.add(new Student(uniqueStudentID(), name, email, password));
        System.out.println(Util.YELLOW_BOLD+"\t"+"Enrolling Student "+name+Util.WHITE_BOLD);
    }
    
    /**
     * Reads email and password from STDIN, checks for input format and uses register 
     * function to register a new student.
     */
    private void register(){
        String email;
        String password;
        System.out.println(Util.GREEN_BOLD+"\t"+"Student Sign Up"+Util.WHITE_BOLD);
        
        while(!checkFormat(email = Util.readString("\tEmail: "), password = Util.readString("\tPassword: ")) || emailExists(email));
        
        register(email, password);
        Util.updateFile(students);
    }
    
    /**
     * Students System Menu - Enables students to login or register. 
     */
    private void menu() {
        char c;
        while ((c = Util.readChoice("\t"+ "Student System (l/r/x): ")) != 'x') {
            switch (c) {
                case 'l':
                    login();
                    break;
                case 'r':
                    register();
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
        System.out.println("\tl - login");
        System.out.println("\tr - register");
        System.out.println("\tx - exit");
    }
}
