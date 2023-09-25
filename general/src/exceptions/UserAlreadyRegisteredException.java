package exceptions;

import java.sql.SQLException;

public class UserAlreadyRegisteredException extends SQLException {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
