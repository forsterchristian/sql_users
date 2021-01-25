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

    // Nimmt eine Liste von Zeilen aus der user-Datei entgegen, gibt eine Liste
    // mit CREATE USER Befehlen und Passwoertern zurueck
    public static List<String> createUserWithUsageRigths(List<String> userList, String databasename) {
        ArrayList<String> output = new ArrayList<String>();
        for (String line : userList) {
            // so sollte eine Zeile aussehen:
            // maxmustermann;maxspasswort;Max;Mustermann;m;123
            // Hier interessant sind Name und Passwort an Position 0 und 1
            String[] userdata = line.split(";");
            String username = userdata[0];
            String password = userdata[1];

            //output.add(String.format("CREATE USER '%s'@'%%' IDENTIFIED BY '%s';", username, password));
            //output.add(String.format("GRANT SELECT, INSERT, UPDATE, DELETE ON `%s`.* TO '%s'@'%%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;", databasename, username));
            output.add(String.format("GRANT CREATE ON `%s`.* TO '%s'@'%%';", databasename, username));
        }
        return output;
    }

    public static List<String> createUserLetters(List<String> userList) {
        ArrayList<String> output = new ArrayList<String>();
        for (String line : userList) {
            String[] userdata = line.split(";");
            String username = userdata[0];
            String password = userdata[1];
            String firstname = userdata[2];
            String gender = userdata[4];

            output.add(String.format("%s %s,%n%nDein Nutzername für den Datenbankzugriff ist:%n%s%nDas Passwort lautet:%n%s%nMit herzlichen Grüßen%n%n", ((gender.equals("m")) ? "Lieber" : "Liebe"), firstname, username, password));
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.printf("Usage: java SQL_users file_with_userdata.csv databasesName%n");
        } else {
            String givenFileNameWithExtension = args[0];
            String[] givenFile = givenFileNameWithExtension.split("\\.");
            if (givenFile.length < 1) {
                System.out.println("Please use a file extension");
                return;
            }
            String givenFileName = givenFile[0];
            String givenFileExtension = givenFile[1];
            if (!givenFileExtension.equals("csv")) {
                System.out.println("Must be a .csv-File!");
                return;
            }
            String databasename = args[1];
            
            List<String> listOfUserdata = new ArrayList<String>();
            listOfUserdata = readUserFile(givenFileNameWithExtension);
            writeListToFile(createUserWithUsageRigths(listOfUserdata, databasename), givenFileName + ".sql");
            // writeListToFile(createUserLetters(listOfUserdata), givenFileName + ".txt");
        }
    }
        

}