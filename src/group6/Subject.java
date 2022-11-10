package group6;

import static group6.Util.*;
import java.util.Random;
import java.util.stream.Stream;

/**
 *
 * @author 236351
 */
public class Subject {

    // Subject Fields
    private int subjectID;
    private int mark;
    private String grade;

    public Subject(int ID) {
        subjectID = ID;
        mark = generatRand(25, 100);
        grade = grade();
    }

    // Getters
    public int getSubjectID() {
        return subjectID;
    }

    public int getMark() {
        return mark;
    }

    // Search for subject by ID
    public boolean match(int ID){
        return this.subjectID == ID;
    }
    
    // Generates random numbers between two numbers
    private int generatRand(int min, int max) {
        return new Random().ints(min, max+1).findAny().getAsInt();
    }
    
    // Determins the grade based on mark
    private String grade(){
        return  mark >= 85 ? "HD":
                mark >= 75 ? "D" :
                mark >= 65 ? "C" :
                mark >= 50 ? "P" : "Z";
    }
    
    @Override
    public String toString() {
        return String.format("[ Subject::%03d -- mark = %-3d-- grade = %4s ]", subjectID, mark, grade);
    }
}
