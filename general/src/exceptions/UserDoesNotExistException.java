package exceptions;

import java.sql.SQLException;

public class UserDoesNotExistException extends SQLException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
