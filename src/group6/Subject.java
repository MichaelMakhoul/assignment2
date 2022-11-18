package group6;
import java.io.Serializable;

/**
* The Subject Class contains data of the subject including ID, mark and grade.
* 
* @author Group-6
*/
public class Subject implements Serializable{

    /**
     * Subject Fields.
     */
    private int subjectID;
    private int mark;
    private String grade;

    /**
     * Subject Class Constructor.
     */
    public Subject(int ID) {
        subjectID = ID;
        mark = Util.generatRand(25, 100);
        grade = Util.grade(mark);
    }

    // Accessors
    
    /**
     * Gives access to subjectID
     * @return subjectID
     */
    public int getSubjectID() {
        return subjectID;
    }

    /**
     * Gives access to mark
     * @return mark
     */
    public int getMark() {
        return mark;
    }
    
    /**
     * Returns subject name
     * @return subject's name
     */
    public String getName(){
        return "Subject-"+subjectID;
    }

    /**
     * Search for subject by ID
     * @param ID - Subject ID
     * @return true or false
     */
    public boolean match(int ID){
        return this.subjectID == ID;
    }
    
    /**
     * Overrides the toString function.
     * @return String format.
     */
    @Override
    public String toString() {
        return String.format("[ Subject::%03d -- mark = %-3d-- grade = %4s ]", subjectID, mark, grade);
    }
}
