package exceptions;

import java.sql.SQLException;

public class PermisionException extends SQLException {
    public PermisionException(String message) {
        super(message);
    }
}
