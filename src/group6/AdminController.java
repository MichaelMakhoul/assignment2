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
public class AdminController {
    
    private List<Student> students = new ArrayList();

    public AdminController() {
        initList();
        System.out.println(students);
//        menu();
    }
    
    public void initList(){
        Database db = new Database();
        students.addAll(db.readStudents());
    }
    
    // Group students by their grades 
    
    // Partition students to pass or fail   ---- Use 'Collectors.partitioningBy' method and passed function from Student class ----
    
    // Remove student  ---- Search student by ID using matchID from Student class and remove it from the list. * Do not forget to save the list again to the file after removal* ----
    
    // Clear students.data file    ---- Use clear function from Database class ----
    
    // Show students  ---- you can use show method from Database class ----
    
    
    // Menu --> Admin System (c/g/p/r/s/x): 
}
