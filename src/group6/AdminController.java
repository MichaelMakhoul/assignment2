/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
*
* @author 236351
*/
public class AdminController {
    
    private List<Student> students = new ArrayList();
    
    public AdminController() {
        initList();
        menu();
    }
    
   // Initialize students list with data from students.data file
    public void initList() {
        Database db = new Database();
        students.addAll(db.readStudents());
    }
    
    // Returns a list of name, ID, grade and average mark for each student. used when grouping students by grade.
    private List<String> detailsList(List<Student> list){
        List<String> temp = new ArrayList();
        for (Student student : list) {
            temp.add(student.getStudentsDetails());
        }
        
        return temp;
    }
    
    // Group students by their grades
    private Map<String, List<Student>> groupByGrades(List<Student> list) {
        return list.stream().filter(Student::hasEnrolled).collect(Collectors.groupingBy(Student::getGrade));
    }
    
    private void groupByGrades(){
        System.out.println(Util.YELLOW_BOLD+"Grade Grouping"+Util.WHITE_BOLD);
        
        Map<String, List<Student>> map = groupByGrades(students);
        
        if(map.size() > 0){
            map.forEach((g, v) -> {
                System.out.println(g + " --> " + detailsList(v));
            });
        } else {
            System.out.println("\t< Nothing to Display >");
        }
    }
    
    // Partition students to pass or fail   ---- Use 'Collectors.partitioningBy' method and passed function from Student class ----
    private Map<Boolean, List<Student>> partition(List<Student> list) {
        return list.stream().filter(Student::hasEnrolled).collect(Collectors.partitioningBy(Student::passed));
    }
    
    private void partition(){
        System.out.println(Util.YELLOW_BOLD+"PASS/FAIL Partition"+Util.WHITE_BOLD);
        
        partition(students).forEach((k, v) -> {
            System.out.println((k ? "PASS" : "FAIL") + " --> "+ v);
        });
    }
    
//    // look up function to search subjects list by ID
//    private Student student(int ID) {
//        return students.stream().filter(p -> p.matchID(ID)).findAny().orElse(null);
//    }

    // updated look up function to search subjects list by ID and return a list
    private List<Student> students(int ID) {
        List<Student> temp = new ArrayList();
        for (Student student:students) {
            if(student.getStudentID() == ID){
                temp.add(student);
            }
        }
        return temp;
    }
    
    // Remove student  ---- Search student by ID using matchID from Student class and remove it from the list. * Do not forget to save the list again to the file after removal* ----
    private void removeStudent(int ID) {
        List<Student> toDelete = students(ID);
        
        if(toDelete.size() > 0){
            students.removeAll(toDelete);
            Util.updateFile(students);
        } else {
            System.err.println(Util.RED_BOLD+"Student "+ID+" does not exist"+Util.WHITE_BOLD);
        }
    }
    
    private void removeStudent(){
        int ID = Util.readNumber("Remove by ID: ");
        removeStudent(ID);
    }
        
    // Clear students.data file    ---- Use clear function from Database class ----
    private void clear(){
        System.out.println(Util.YELLOW_BOLD+"Clearing students database"+Util.WHITE_BOLD);
        
        char choice = Util.readString(Util.RED_BOLD+"Are you sure you want to clear the database (Y)ES / (N)O: "
                      +Util.WHITE_BOLD).charAt(0);
        if(choice == 'Y'){
            try {
                new Database().clear();
                System.out.println(Util.YELLOW_BOLD+"Students data cleared"+Util.WHITE_BOLD);
                students.clear();
            } catch (IOException ex) {
                System.out.println(Util.RED_BOLD+"Unable to clear students database"+Util.WHITE_BOLD);
            }
        }
    }
    
    // Show students  ---- you can use show method from Database class ----   
    private void show(){
        System.out.println(Util.YELLOW_BOLD+"Student List"+Util.WHITE_BOLD);
        if(students.size() > 0){
            try {
                new Database().show();
            } catch (IOException ex) {
                System.out.println("Unable to read data from students database");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("\t< Nothing to Display >");
        }
    }

    // Menu --> Admin System (c/g/p/r/s/x):
    private char readChoice() {
        System.out.print("Choice(c/g/p/r/s/x): ");
        return In.nextChar();
    }
    
    private void menu() {
        char c;
        while ((c = readChoice()) != 'x') {
            switch (c) {
               case 'c':
                    clear();
                    break;
                case 'g':
                    groupByGrades();
                    break;
                case 'p':
                    partition();
                    break;
                case 'r':
                    removeStudent();
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
       System.out.println("c - clear all students from file");
       System.out.println("g - group students by their grades");
       System.out.println("p - partition students to pass or fail");
       System.out.println("r - remove student from file");
       System.out.println("s - show students");
       System.out.println("x - exit");
    }
}