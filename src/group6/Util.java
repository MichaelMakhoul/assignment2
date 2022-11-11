/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group6;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 236351
 */
public class Util {

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
    
    // Initializes the list with data from students.data file
    public static void initList(List<Student> list){
        Database d = new Database();
        list = d.readStudents();
    }

    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE
}
