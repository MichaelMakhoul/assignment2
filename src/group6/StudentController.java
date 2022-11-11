/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 236351
 */
public class StudentController {
    private List<Student> students = new ArrayList();

    public StudentController() {
        initList();
        menu();
    }
    
    public void initList(){
        Database db = new Database();
        students.addAll(db.readStudents());
    }
    
    private boolean emailRegex(String email){
        String emailRegex = ".*\\..*@university.com";
        return Util.checkRegex(email, emailRegex);
    }
    
    private boolean passwordRegex(String password){
        String passwordRegex = "^[A-Z][a-z]{5,}[0-9]{3,}$";
        return Util.checkRegex(password, passwordRegex);
    }
    
    private Student existingStudent(String email, String password){
        return students.stream().
        filter(p -> (p.getEmail().equals(email) && p.getPassword().equals(password))).
        findAny().orElse(null);
    }
    
    private boolean checkFormate(String email, String password){
        if(emailRegex(email) && passwordRegex(password)){
            System.out.println(Util.YELLOW_BOLD+"email and password format acceptable"+Util.WHITE_BOLD);
            return true;
        } else {
            System.err.println(Util.RED_BOLD+"Incorrect email or password format"+Util.WHITE_BOLD); 
            return false;
        }
    }
    
    private Student login(String email, String password){
        Student student = existingStudent(email, password);
        
        if(student == null){
            System.out.println(Util.RED_BOLD+"Student does not exist"+Util.WHITE_BOLD);            
        } else {
            return student; 
        }
        
        return null;
    }
    
    
    private void login(){
        String email;
        String password;
        
        while(!checkFormate(email = Util.readString("Email: "), password = Util.readString("Password: ")));
        
        Student student = login(email, password);
        
        if(student != null){
            new CourseController(student);
        }
    }
    
    // Updates students.data file after modifying the list
    private void updateFile(){
            Database db = new Database();
        try {
            db.save(students);
        } catch (IOException ex) {
            System.out.println(Util.RED_BOLD+"Unable to save data to students.data file"+Util.WHITE_BOLD);
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
    
    private void register(String email, String password){
        String name = Util.readString("Name: ");
        students.add(new Student(uniqueStudentID(), name, email, password));
    }
    
    private void register(){
        String email;
        String password;
        while(!checkFormate(email = Util.readString("Email: "), password = Util.readString("Password: ")));
        register(email, password);
        updateFile();
    }
    
    public char readChoice() {
        System.out.print("\tChoice(l/r/x): ");
        return In.nextChar();
    }
    
    private void menu() {
        char c;
        while ((c = Character.toLowerCase(readChoice())) != 'x') {
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
        System.out.println("l - login");
        System.out.println("r - register");
        System.out.println("x - exit");
    }
}
