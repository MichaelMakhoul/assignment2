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
        try {
            u.menu();   
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(Util.RED_BOLD+"Unknown command"+Util.WHITE_BOLD);
        }
    }
    
    private void adminSystem(){
        new AdminController();
    }
    
    private void studentSystem(){
        new StudentController();
    }
    
    public static char readChoice() {
        System.out.print("University System: (A)dmin, (S)tudent, or X:  ");
        return In.nextChar();
    }
    
    // Menu --> University System: (A)dmin, (S)tudent, or X: 
    private void menu() throws StringIndexOutOfBoundsException{
        char c;
        while ((c = readChoice()) != 'X') {
            switch (c) {
                case 'A':
                    adminSystem();
                    break;
                case 'S':
                    studentSystem();
                    break;
                default:
                    help();
                    break;
            }
        }
        
        System.out.println(Util.YELLOW_BOLD+"Thank You"+Util.WHITE_BOLD);
    }

    private void help() {
        System.out.println("A - admin");
        System.out.println("S - student");
        System.out.println("X - exit");
    }
}
