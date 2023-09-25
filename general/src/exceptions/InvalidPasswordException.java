package exceptions;

import java.sql.SQLException;

public class InvalidPasswordException extends SQLException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
