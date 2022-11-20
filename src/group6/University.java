package group6;

/**
 * This interface helps administrators to manage students enrollments
 * and shows various types of reports about students marks and grades
 * As well as, helping students to manage their accounts and review their marks.
 * 
 * The University System enables access to the Student system menu and to 
 * the Admin system menu.
 * 
 * @author Group-6
 */
public class University {
    
    /**
     * University Class main method.
     */
    public static void main(String[] args){
        University u = new University();
        try {
            u.menu();   
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(Util.RED_BOLD+"Unknown command"+Util.WHITE_BOLD);
        }
    }
    
    /**
     * Enables access to Admin System.
     */
    private void adminSystem(){
        new AdminController();
    }
    
    /**
     * Enables access to Students System.
     */
    private void studentSystem(){
        new StudentController();
    }
    
    /**
     * University System Menu - Enables users to choose the required system.
     * 
     * @throws StringIndexOutOfBoundsException 
     */
    private void menu() throws StringIndexOutOfBoundsException{
        char c;
        while ((c = Util.readChoice("University System: (A)dmin, (S)tudent, or X: ")) != 'X') {
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

    /**
     * Displays the available options for the user to choose from.
     */
    private void help() {
        System.out.println("A - admin");
        System.out.println("S - student");
        System.out.println("X - exit");
    }
}
