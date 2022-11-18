package group6;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
* The Database Class connects the system to students database -
* the class reads and save data from students.data file.
* 
* @author Group-6
*/
public class Database {

    private String filename = "students.data";
    private Path filePath;

    /**
     * Database Class Constructor.
     */ 
    public Database() {
        setup();
    }
    
    /**
     * Checks if the file 'students.data exists before creating it
     */
    private void setup() {
        filePath = Paths.get(filename);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException ex) {
                System.out.println(Util.RED_BOLD + "Unable to create file " + filename + Util.WHITE_BOLD);
            }
        }
    }

    /**
     * Saves objects to a file.
     * @param list - a list of student objects
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void save(List<Student> list) throws FileNotFoundException, IOException {
        File file = filePath.toFile();
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        objOut.writeObject(list);

        objOut.close();
        fileOut.close();
    }

    /**
     * Reads objects from a file.
     * @param path - students.data file location.
     * @return list of student objects
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    private List<Student> readStudents(Path path) throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = path.toFile();
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        List<Student> temp = (List<Student>) objectIn.readObject();

        objectIn.close();
        fileIn.close();

        return temp;
    }

    /**
     * Uses readStudents function to read data from the students file.
     * @return list of student objects
     */
    public List<Student> readStudents() {
        List<Student> temp = new ArrayList();
        try {
            temp.addAll(readStudents(filePath));
        } catch (EOFException ex) {
            System.out.println("Students database is empty");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(Util.RED_BOLD + "Unable to read from file " + filename + Util.WHITE_BOLD);
        }

        return temp;
    }

    /**
     * Displays list of students from the students.data file.
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException 
     */
    public void show() throws IOException, FileNotFoundException, ClassNotFoundException {
        List<Student> temp = readStudents(filePath);
        temp.forEach(s -> System.out.println("\t"+s));
    }

    /**
     * Empties the students.data file by overriding the file content with new empty arrayList.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void clear() throws FileNotFoundException, IOException {
        File f = filePath.toFile();

        FileOutputStream fOut = new FileOutputStream(f);
        ObjectOutputStream objOut = new ObjectOutputStream(fOut);

        objOut.writeObject(new ArrayList<Student>());

        objOut.close();
        fOut.close();
    }
}
