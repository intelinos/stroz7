package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Database {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private final String login;
    private final String password;

    public Database(File file) throws FileNotFoundException {
            Scanner scanner = new Scanner(file);
            String[] lines = scanner.nextLine().split(":");
            this.login = lines[3].trim();
            this.password = lines[4].trim();
            //System.out.println(login);
            //System.out.println(password);

    }

    public DBConnection createConnection() throws SQLException {
        return new DBConnection(URL, login, password);
    }
}

