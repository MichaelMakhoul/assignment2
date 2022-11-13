/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group6;

/**
 *
 * @author 236351
 */
public class University {
    
    public static void main(String[] args){
        University u = new University();
        u.menu();
    }
    
    private void adminSystem(){
        new AdminController();
    }
    
    private void studentSystem(){
        new StudentController();
    }
    
    public static char readChoice() {
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
