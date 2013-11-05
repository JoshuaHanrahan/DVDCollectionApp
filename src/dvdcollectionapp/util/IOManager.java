package dvdcollectionapp.util;

import dvdcollectionapp.model.DVD;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 * IOManager class
 * @author Josh Hanrahan
 */
public class IOManager {

    /**
     * creates a new array list of DVD objects.
     *
     * @param file
     * @return d a DVD object array list
     */
    public static ArrayList<DVD> readTextDatabase(File file) {
        //-------------------------------------
        ArrayList<DVD> d = new ArrayList<>();
        FileReader fr;
        BufferedReader br;
        String line;
        DVD newDVD;

        System.out.println("File name and path: " + file.getName() + " " + file);

        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.err.println("No DVD Database ");
            return d;
        }
        try {
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                newDVD = DVD.createDVD(line);
                if (newDVD != null) {
                    d.add(newDVD);
                }
            }
        } catch (EOFException eof) {
            System.out.println("Finished reading file\n");
        } catch (IOException e) {
            System.err.println("Error during read\n" + e.toString());
        }
        try {
            fr.close();
        } catch (IOException e) {
            System.err.println("File not closed properly\n" + e.toString());
            System.exit(1);
        }
        return d;
    }

    /**
     * reads in a file. catches exceptions such as file not found and IO errors.
     *
     * @param file the file that is been passed in to be read
     *
     */
    private String readFile(File file) {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IOManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(IOManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

    /**
     * Writes a DVD array list to a CSV file
     *
     * @param file the file to be written to
     * @param d the DVD object
     */
    public static void writeTextDatabase(File file, ObservableList<DVD> d) {
        //-----------------------------------

        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (DVD dvd : d) {
                    bw.write(dvd.toCSV() + "\n");
                }
                System.out.println("finished writing...");
                bw.flush();
            }
        } catch (IOException e) {
            System.err.println("File not closed properly\n" + e.toString());
            System.exit(1);
        }
    }

    /**
     * Reads from an XML file
     *
     * @param fileName the fileName of the XML
     * @return an Arraylist of DVD's
     */
    public static ArrayList<DVD> readXMLDatabase(String fileName) {
        //-------------------------------------
        ArrayList<DVD> d = new ArrayList<>();
        XMLDecoder x;

        try {
            x = new XMLDecoder(
                    new BufferedInputStream(
                    new FileInputStream(fileName)));
            d = (ArrayList<DVD>) x.readObject();

        } catch (FileNotFoundException e) {
            System.err.println("No DVD Database ");
            return d;
        }
        x.close();
        return d;
    }

    /**
     * Writes a DVD array list to an XML file
     *
     * @param fileName the name of the file being written to
     * @param d and observable list of DVD objects
     */
    public static void writeXMLDatabase(String fileName, ObservableList<DVD> d) {
        //-----------------------------------

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            for (DVD dvd : d) {
                encoder.writeObject(dvd);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not closed properly\n" + e.toString());
            System.exit(1);
        }
    }

}
