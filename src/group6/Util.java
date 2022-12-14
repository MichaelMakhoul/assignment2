package group6;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Util Class contains a number of methods to simplify the functionality of
 * other classes and ensures better re-usability.
 *
 * @author Group-6
 */
public class Util {

    /**
     * Regex formats for email and password.
     */
    public static final String emailRegex = "^[A-Za-z]*\\.[A-Za-z]*@university.com$";
    public static final String passwordRegex = "^[A-Z][A-Za-z]{5,}[0-9]{3,}$";

    /**
     * A group of colours - used to change the output when the program displays
     * data to STDOUT.
     */
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[0m";  // WHITE

    /**
     * Read integer value.
     *
     * @param prompt - String prompt
     * @return users input as integer
     */
    public static int readNumber(String prompt) {
        System.out.print(prompt);
        String number;

        while (!(number = In.nextLine()).chars().allMatch(Character::isDigit) || number.isEmpty()) {
            int tabs = (int) prompt.chars().filter(c -> c == '\t').count();
            
            String indent = prompt.substring(0, tabs);

            System.out.printf(RED_BOLD + indent + "%s %n" + WHITE_BOLD + "%s", "Invalid input", prompt);
        }

        return Integer.valueOf(number);
    }

    /**
     * Read String value.
     *
     * @param prompt - String prompt
     * @return users input as string
     */
    public static String readString(String prompt) {
        System.out.print(prompt);
        return In.nextLine();
    }

    /**
     * Generates random numbers between two numbers.
     *
     * @param min - the lower boned of randomization.
     * @param max - the upper boned of randomization.
     * @return random integer.
     */
    public static int generatRand(int min, int max) {
        return new Random().ints(min, max + 1).findAny().getAsInt();
    }

    /**
     * Determines the grade based on the mark
     *
     * @param mark - The mark to calculate the grade based on
     * @return grade
     */
    public static String grade(double mark) {
        return mark >= 85 ? "HD"
                : mark >= 75 ? "D"
                        : mark >= 65 ? "C"
                                : mark >= 50 ? "P" : "Z";
    }

    /**
     * Checks if the input matches the correct format as specified in regex.
     *
     * @param input - String input
     * @param regex - regex
     * @return true or false
     */
    public static boolean checkRegex(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        return m.find();
    }

    /**
     * Checks if the email satisfies the required regex format.
     *
     * @param email - Students email
     * @return true or false
     */
    public static boolean emailRegex(String email) {
        return Util.checkRegex(email, emailRegex);
    }

    /**
     * Checks if the password satisfies the required regex format.
     *
     * @param password - Students password
     * @return true or false
     */
    public static boolean passwordRegex(String password) {
        return checkRegex(password, passwordRegex);
    }

    /**
     * Returns the length of the longest name in the list of students who has
     * enrolled in one subject at least - Used to align the output.
     *
     * @param list - list of student objects
     * @return length of the longest student name in the list.
     */
    public static int maxNameLength(List<Student> list) {
        return list.stream().filter(Student::hasEnrolled).mapToInt(s -> s.getName().length()).max().getAsInt();
    }

    /**
     * Returns the length of the longest name in the list of students - Used to
     * align the output.
     *
     * @return length of the longest student name of all students.
     */
    public static int maxNameLength() {
        List<Student> students = new Database().readStudents();
        return students.stream().mapToInt(s -> s.getName().length()).max().getAsInt();
    }

    /**
     * Updates students.data file after modifying the list.
     *
     * @param list - list of student objects
     */
    public static void updateFile(List<Student> list) {
        Database db = new Database();
        try {
            db.save(list);
        } catch (IOException ex) {
            System.out.println(RED_BOLD + "Unable to save data to students.data file" + WHITE_BOLD);
        }
    }
    
    /**
     * Displays the different options to help user to choose from the menu.
     * @param prompt - String to show available commands
     * @return users choice.
     */
    public static char readChoice(String prompt) {
        System.out.print(CYAN_BOLD+prompt+WHITE_BOLD);
        String choice;
        
        while(((choice = In.nextLine()).isEmpty())){
            int tabs = prompt.lastIndexOf('\t');
            String indent = prompt.substring(0, tabs + 1);

            System.out.printf(RED_BOLD + indent + "%s %n" + CYAN_BOLD + "%s", "Invalid input", prompt);
        }
        
        return choice.charAt(0);
    }
}
