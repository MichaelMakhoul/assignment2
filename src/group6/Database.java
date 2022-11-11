/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group6;

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
 *
 * @author 236351
 */
public class Database {
    private String filename = "students.data";
    private Path filePath;

    public Database() {
        setup();
    }
    
    // Checks if the file 'students.data exists' before creating it
    private void setup() {
        filePath = Paths.get(filename);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException ex) {
                System.out.println(Util.RED_BOLD+"Unable to create file "+filename+Util.WHITE_BOLD);
            }
        }
    }

    //saving objects to a file
    public void save(List<Student> list) throws FileNotFoundException, IOException {
        File file = filePath.toFile();
        System.out.println("File path: "+file);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        objOut.writeObject(list);

        objOut.close();
        fileOut.close();
    }
    
    private List<Student> readStudents(Path path) throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = path.toFile();
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        List<Student> temp = (List<Student>) objectIn.readObject();

        objectIn.close();
        fileIn.close();

        return temp;
    }
    
    public List<Student> readStudents(){
        List<Student> temp = new ArrayList();
        try {
            temp.addAll(readStudents(filePath));
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(Util.RED_BOLD+"Unable to read from file "+filename+Util.WHITE_BOLD);
        }
        
        return temp;
    }

    private void show() throws IOException, FileNotFoundException, ClassNotFoundException {
        List<Student> temp = readStudents(filePath);
        temp.forEach(System.out::println);
    }

    public void clear() throws FileNotFoundException, IOException {
        File f = filePath.toFile();
        
        FileOutputStream fOut = new FileOutputStream(f);
        ObjectOutputStream objOut = new ObjectOutputStream(fOut);
        
        objOut.writeObject(new ArrayList<Student>());

//        subjects.clear();

        objOut.close();
        fOut.close();
    }
    
//    public static void main(String[] args) {
//        new Database();
//    }
}
