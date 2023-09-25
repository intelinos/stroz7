package db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Database {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private final String login;
    private final String password;

    public Database(String file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            String[] lines = scanner.nextLine().split(":");
            this.login = lines[3];
            this.password = lines[4];
        }

    }

    public DBConnection createConnection() throws SQLException {
        return new DBConnection(URL, login, password);
    }
}

