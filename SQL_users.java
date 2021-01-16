import java.util.List;
import java.util.ArrayList;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
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

    // Nimmt eine Liste von Strings entgegen und einen Dateinamen, ueberschreibt
    // eine eventuell vorhandene Datei und schreibt die Liste zeilenweise hinein
    public static void writeListToFile(List<String> toWrite, String filename) throws IOException {
        Path file = Paths.get(filename);
        Files.write(file, toWrite, StandardCharsets.UTF_8);
    }

    public static List<String> createUser(List<String> userList) {
        ArrayList<String> output = new ArrayList<String>();
        for (String line : userList) {
            String[] userdata = line.split(";");
            output.add(String.format("CREATE USER %s IDENTIFIED BY %s;", userdata[0], userdata[1]));
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = new ArrayList<String>();
        lines = readUserFile("userexample.csv");
        for (String line : lines) {
            System.out.println(line);
        }
        writeListToFile(createUser(lines), "out.txt");
    }

}