/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group6;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 236351
 */
public class StudentController {
    private List<Student> students = new ArrayList();

    // Constructor
    public StudentController() {
        initList();
        menu();
    }
    
    // Initialize students list with data from students.data file
    public void initList(){
        Database db = new Database();
        students.addAll(db.readStudents());
    }
    
    // Check if the student exists 
    private Student existingStudent(String email, String password){
        return students.stream().
        filter(p -> (p.getEmail().equals(email) && p.getPassword().equals(password))).
        findAny().orElse(null);
    }
    
    private boolean checkFormate(String email, String password){
        if(Util.emailRegex(email) && Util.passwordRegex(password)){
            System.out.println(Util.YELLOW_BOLD+"\temail and password format acceptable"+Util.WHITE_BOLD);
            return true;
        } else {
            System.err.println(Util.RED_BOLD+"\tIncorrect email or password format"+Util.WHITE_BOLD); 
            return false;
        }
    }
    
    private Student login(String email, String password){
        Student student = existingStudent(email, password);
        
        if(student != null){
            return student;             
        } else {
            System.out.println(Util.RED_BOLD+"\tStudent does not exist"+Util.WHITE_BOLD);
        }
        
        return null;
    }
    
    private void login(){
        String email;
        String password;
        
        while(!checkFormate(email = Util.readString("Email: "), password = Util.readString("Password: ")));
        
        Student student = login(email, password);
        
        if(student != null){
            new CourseController(student.getStudentID());
        }
    }

    // look up function to search students list by ID
    private Student student(int ID) {
        return students.stream().filter(p -> p.matchID(ID)).findAny().orElse(null);
    }
    
    // Generates unique subjectID
    private int uniqueStudentID() {
        int ID = Util.generatRand(1, 999999);

        while (student(ID) != null) {
            return Util.generatRand(1, 999999);
        }

        return ID;
    }
    
    // look up function to search students list by email
    private Student emailExists(String email) {
        return students.stream().filter(p -> p.matchEmail(email)).findAny().orElse(null);
    }
    
    private void register(String email, String password){
        String name = Util.readString("Name: ");
        students.add(new Student(uniqueStudentID(), name, email, password));
    }
    
//    private void register(){
//        String email;
//        String password;
//        System.out.println(Util.GREEN_BOLD+"\t"+"Student Sign Up"+Util.WHITE_BOLD);
//       
//        while(emailExists(email = Util.readString("Email: ")) !=null){
//          System.out.println(Util.RED_BOLD+"\t"+"Email already exists"+Util.WHITE_BOLD);  
//        
//        }
//         while(!checkFormate(email = Util.readString("Email: "), password = Util.readString("Password: ")));
//        register(email, password);
//        Util.updateFile(students);
//    }
    
    private void register(){
        String email;
        String password;
        while(!checkFormate(email = Util.readString("Email: "), password = Util.readString("Password: ")));
//        while(emailExists() != null){
//            email = Util.readString("Email: ");
//        }
        register(email, password);
        Util.updateFile(students);
    }
    
    public char readChoice() {
        System.out.print("\tChoice(l/r/x): ");
        return In.nextChar();
    }
    
    private void menu() {
        char c;
        while ((c = readChoice()) != 'x') {
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

    private void help() {
        System.out.println("\tl - login");
        System.out.println("\tr - register");
        System.out.println("\tx - exit");
    }
}
