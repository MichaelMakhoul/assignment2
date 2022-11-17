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
public class CourseController {
    Student student;
    private List<Student> students = new ArrayList();
    
    // Constructor 
    public CourseController(int ID) {
        initList();
        this.student = setSession(ID);
        try {
            menu();   
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(Util.RED_BOLD+"Unknown command"+Util.WHITE_BOLD);
        }
    }
    
    // Initialize students list with data from students.data file
    public void initList(){
        Database db = new Database();
        students.addAll(db.readStudents());
    }
    
    private Student setSession(int ID){
        return students.stream().filter(s -> s.getStudentID() == ID).findAny().orElse(null);
    }
    
    // Updates students.data file after modifying the list
    private void updateList(Student s){
        int position = students.indexOf(s);
        students.set(position, student);
        
        Util.updateFile(students);
    }
    
    private void enrol(){
        if(student.checkMaxCapacity()){
            student.enrolSubject();
            updateList(student);
        }else{
            System.out.println(Util.RED_BOLD+"Students are allowed to enrol in 4 subjects only"+Util.WHITE_BOLD);
        }
        
    }
    
    // Checks if the email and password matches the correct format
    private String checkPasswordFormate(String password){
//        if(!accepted){
//            System.out.println(Util.RED_BOLD+"\tIncorrect password format"+Util.WHITE_BOLD);
//        }
//        
//        return accepted;
            
//        String password = Util.readString("New Password: ");
        
//        boolean accepted = Util.passwordRegex(password);
        
        while(!Util.passwordRegex(password)){
            System.out.println(Util.RED_BOLD+"\tIncorrect password format"+Util.WHITE_BOLD);
            password = Util.readString("New Password: ");
        }

        return password;
    }
    
    private boolean passwordMatch(String password, String confirm){
        
        password = Util.readString("New password: ");
        
        boolean matches = password.equals(confirm);
        
        while(!matches){
            System.out.println(Util.RED_BOLD+"\tPassword does not match - try again"+Util.WHITE_BOLD);
            password = Util.readString("New password: ");
        }
        
        return matches;
    }
    
    private void confirmPassword(String newPassword){
        String confirmPassword;
        
        while(!passwordMatch(newPassword, confirmPassword = Util.readString("Confirm Password: ")));
        
        student.setPassword(newPassword);
        updateList(student);
    }
    
    private void change(){
        String newPassword = "";
        
//        // Check regex
//        while(!Util.passwordRegex(newPassword = Util.readString("New Password: "))){
//            System.out.println(Util.RED_BOLD+"Incorrect password format"+Util.WHITE_BOLD);
//        }
        
        

        // Passwords match
//        while(!checkPasswordFormate(newPassword = Util.readString("New Password: ")));
        
        confirmPassword(newPassword);
      
        
        
//            if(!passwordMatch(newPassword, confirmPassword)){
//                confirmPassword = Util.readString("Confirm Password: ");
//            }
        
        
//        student.setPassword(newPassword);
//        updateList(student);
        
        // Check regex 
//        if(Util.passwordRegex(newPassword) && Util.passwordRegex(confirmPassword)){
//            student.setPassword(newPassword);
//            updateList(student);
//        }else{
//            System.out.println(Util.RED_BOLD+"Incorrect password format"+Util.WHITE_BOLD);
//        }
    }
    
    private void remove(){
        int ID = Util.readNumber("Remove Subject by ID: ");
        student.dropSubject(ID);
        updateList(student);
    }
    
    private void show(){
        student.showSubjects();
    }
    
    public char readChoice() {
        System.out.print("\t\tChoice(c/e/r/s/x): ");
        return In.nextChar();
    }
    
    private void menu() {
        char c;
        while ((c = readChoice()) != 'x') {
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

    private void help() {
        System.out.println("c - change");
        System.out.println("e - enrol");
        System.out.println("r - remove");
        System.out.println("s - show");
        System.out.println("x - exit");
    }
}
