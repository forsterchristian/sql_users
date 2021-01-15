import java.util.List;
import java.util.ArrayList;
import java.nio.file.*;
import java.io.IOException;

public class SQL_users {
    
    // Nimmt einen Dateinamen entgegen (Datei muss im gleichen Verzeichnis liegen)
    // Gibt den Inhalt der Datei zeilenweise in einer Liste zurueck
    public static List<String> readUserFile(String filename) throws IOException {
        ArrayList<String> output = new ArrayList<String>();

        for (String line : Files.readAllLines(Paths.get(filename))) {
            output.add(line);
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        for (String line : readUserFile("userexample.csv")) {
            System.out.println(line);
        }
    }

}