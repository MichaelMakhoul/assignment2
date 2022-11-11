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
public class University {
    private List<Student> students = new ArrayList();
    
    public static void main(String[] args){
        University u = new University();
        u.menu();
    }

    public University() {
        Util.initList(students);
    }  
    
    private void adminSystem(){
        new AdminController();
    }
    
    private void studentSystem(){
        new StudentController();
    }
    
    public char readChoice() {
        System.out.print("University System: (A)dmin, (S)tudent, or X: ");
        return In.nextChar();
    }
    
    // Menu --> University System: (A)dmin, (S)tudent, or X: 
    private void menu() {
        char c;
        while ((c = Character.toLowerCase(readChoice())) != 'x') {
            switch (c) {
                case 'a':
                    adminSystem();
                    break;
                case 's':
                    studentSystem();
                    break;
                default:
                    help();
                    break;
            }
        }
    }

    private void help() {
        System.out.println("A - admin");
        System.out.println("s - student");
        System.out.println("x - exit");
    }
}
