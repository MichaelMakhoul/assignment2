package group6;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author 236351
 */
public class Subject implements Serializable{

    // Subject Fields
    private int subjectID;
    private int mark;
    private String grade;

    public Subject(int ID) {
        subjectID = ID;
        mark = Util.generatRand(25, 100);
        grade = grade();
    }

    // Accessors
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
