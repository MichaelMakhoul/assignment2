/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group6;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 236351
 */
public class Util {
    static final String emailRegex = ".*\\..*@university.com";
    static final String passwordRegex = "^[A-Z][a-z]{5,}[0-9]{3,}$";

    // Read integer value
    public static int readNumber(String prompt) {
        System.out.print(prompt);
        return In.nextInt();
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return In.nextLine();
    }
    
    // Generates random numbers between two numbers
    public static int generatRand(int min, int max) {
        return new Random().ints(min, max + 1).findAny().getAsInt();
    }

    public static boolean checkRegex(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        
        return m.find();
    }
    
    public static boolean emailRegex(String email){
        return Util.checkRegex(email, emailRegex);
    }
    
    public static boolean passwordRegex(String password){
        return checkRegex(password, passwordRegex);
    }
    
    public static int maxNameLength(List<Student> students){
        return students.stream().filter(Student::hasEnrolled).mapToInt(s -> s.getName().length()).max().getAsInt();
    }
    
    public static int maxNameLength(){
        List<Student> students = new Database().readStudents();
        return students.stream().mapToInt(s -> s.getName().length()).max().getAsInt();
    }

    // Updates students.data file after modifying the list
    public static void updateFile(List<Student> list){
            Database db = new Database();
        try {
            db.save(list);
        } catch (IOException ex) {
            System.out.println(Util.RED_BOLD+"Unable to save data to students.data file"+Util.WHITE_BOLD);
        }
    }

    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[0m";  // WHITE
}


// Regex on each new password 

// length - Done

// Regex for email and password on login - Done

// Group by grade instead of avg-mark - Done
// email unique - Done
// Try group with empty subject list - Done
