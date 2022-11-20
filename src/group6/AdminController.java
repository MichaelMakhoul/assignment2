package group6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
* The AdminController Class, Helps administrators to manage and display students data.
* 
* @author Group-6
*/
public class AdminController {
    
    /**
     * List of students, an instance of Student Class - used to hold
     * students data from the database.
     */
    private List<Student> students = new ArrayList();
    
    /**
     * AdminController Class Constructor.
     */    
    public AdminController() {
        initList();
        menu();
    }
    
    /**
     * Initialize students list with data from students.data file.
     */
    public void initList() {
        Database db = new Database();
        students = db.readStudents();
    }
    
    /**
     * Returns a list of name, ID, grade and average mark for each student - used when grouping students by grade.
     * 
     * @param list - List of Students.
     * @return List of String type.
     */
    private List<String> printStudentDetails(List<Student> list){
        List<String> temp = new ArrayList();
        for (Student student : list) {
            temp.add(student.printDetails(Util.maxNameLength(students)));
        }
        
        return temp;
    }
    
    /**
     * Groups students by their grades.
     * 
     * @param list - List of Students.
     * @return a map of <String, List<Student>> type
     */
    private Map<String, List<Student>> groupByGrades(List<Student> list) {
        return list.stream().filter(Student::hasEnrolled).collect(Collectors.groupingBy(Student::getGrade));
    }
    
    /**
     * Prints out students grouped by their grades. 
     */
    private void groupByGrades(){
        System.out.println(Util.YELLOW_BOLD+"\tGrade Grouping"+Util.WHITE_BOLD);
        
        Map<String, List<Student>> map = groupByGrades(students);
        
        if(map.size() > 0){
            map.forEach((g, v) -> {
                System.out.println("\t"+g + " --> " + printStudentDetails(v));
            });
        } else {
            System.out.println("\t\t< Nothing to Display >");
        }
    }
    
    
    /**
     * Partition students by their final grade to pass or fail.
     * @param list - List of Students.
     * @return a map of <Boolean, List<Student>> type
     */
    private Map<Boolean, List<Student>> partition(List<Student> list) {
        return list.stream().filter(Student::hasEnrolled).collect(Collectors.partitioningBy(Student::passed));
    }
    
    /**
     * Prints out students partitioned by their final grades. 
     */
    private void partition(){
        System.out.println(Util.YELLOW_BOLD+"\tPASS/FAIL Partition"+Util.WHITE_BOLD);
        
        partition(students).forEach((k, v) -> {
            System.out.println((k ? "\tPASS" : "\tFAIL") + " --> "+ printStudentDetails(v));
        });
    }

    /**
     * Updated look up function to search subjects list.
     * @param ID - Students ID
     * @return a list of matched students.
     */
 
    private List<Student> students(int ID) {
        List<Student> temp = new ArrayList();
        for (Student student:students) {
            if(student.getStudentID() == ID){
                temp.add(student);
            }
        }
        return temp;
    }
    
    /**
     * Remove student from database
     * @param ID - Students ID
     */
    private void removeStudent(int ID) {
        List<Student> toDelete = students(ID);
        
        if(toDelete.size() > 0){
            System.out.println(Util.YELLOW_BOLD+"\tRemoving student "+ID+" Account"+Util.WHITE_BOLD);
            students.removeAll(toDelete);
            Util.updateFile(students);
        } else {
            System.err.println(Util.RED_BOLD+"\tStudent "+ID+" does not exist"+Util.WHITE_BOLD);
        }
    }
    
    /**
     * Reads users input and runs removeStudent function.
     */
    private void removeStudent(){
        int ID = Util.readNumber("\tRemove by ID: ");
        removeStudent(ID);
    }
    
    /**
     * Clear students database.
     */
    private void clear(){
        System.out.println(Util.YELLOW_BOLD+"\tClearing students database"+Util.WHITE_BOLD);
        
        String choice = Util.readString(Util.RED_BOLD+"\tAre you sure you want to clear the database (Y)ES / (N)O:"
                      +Util.WHITE_BOLD);
        
        while(choice.length() == 0 || choice.charAt(0) != 'N'){
            if(choice.length() != 0 && choice.charAt(0) == 'Y'){
                try {
                    new Database().clear();
                    System.out.println(Util.YELLOW_BOLD+"\tStudents data cleared"+Util.WHITE_BOLD);
                    students.clear();
                    break;
                } catch (IOException ex) {
                    System.out.println(Util.RED_BOLD+"\tUnable to clear students database"+Util.WHITE_BOLD);
                }
            }
            
            System.out.println(Util.RED_BOLD+"\tInvalid input "+Util.WHITE_BOLD);
            choice = Util.readString(Util.RED_BOLD+"\tAre you sure you want to clear the database (Y)ES / (N)O:"
                      +Util.WHITE_BOLD);
        }
        
        
    }
    
    /**
     * Show students.
     */
    private void show(){
        System.out.println(Util.YELLOW_BOLD+"\tStudent List"+Util.WHITE_BOLD);
        if(students.size() > 0){
            try {
                new Database().show();
            } catch (IOException ex) {
                System.out.println("\tUnable to read data from students database");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("\t\t< Nothing to Display >");
        }
    }
    
    /**
     * Admin System Menu - Enables users to choose the required action.
     */
    private void menu() {
        char c;
        while ((c = Util.readChoice("\t"+"Admin System (c/g/p/r/s/x):")) != 'x') {
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
     
    /**
     * Displays the available options for the user to choose from.
     */
    private void help() {
       System.out.println("\tc - clear all students from file");
       System.out.println("\tg - group students by their grades");
       System.out.println("\tp - partition students to pass or fail");
       System.out.println("\tr - remove student from file");
       System.out.println("\ts - show students");
       System.out.println("\tx - exit");
    }
}