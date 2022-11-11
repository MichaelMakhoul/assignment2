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
public class CourseController {
    Student student;
    private List<Student> students = new ArrayList();
    
    public CourseController(Student s) {
        this.student = s;
        menu();
    }
    
    // Updates students.data file after modifying the list
    private void updateList(Student s){
        int position = students.indexOf(s);
        students.set(position, student);
        
        Database db = new Database();
        
        try {
            db.save(students);
        } catch (IOException ex) {
            System.out.println(Util.RED_BOLD+"Unable to save data to students.data file"+Util.WHITE_BOLD);
        }
    }
    
    private void enrol(){
        if(student.subjectMaxcapacity()){
            student.enrolSubject();
        }
        
    }
    
    private void change(){
        String newPassword = Util.readString("New Password: ");
        String confirmPassword;
        while(!(confirmPassword = Util.readString("Confirm password: ")).equals(newPassword)){
            System.out.println(Util.RED_BOLD+"Password does not match - try again"+Util.WHITE_BOLD);
        }
        
        student.setPassword(confirmPassword);
//        updateList(student);
    }
    
    private void remove(){}
    
    private void show(){}
    
    public char readChoice() {
        System.out.print("\t\tChoice(c/e/r/s/x): ");
        return In.nextChar();
    }
    
    private void menu() {
        char c;
        while ((c = Character.toLowerCase(readChoice())) != 'x') {
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
