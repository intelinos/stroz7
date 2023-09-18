package exceptions;

/**
 * Исключение, возникающее при невалидном значении во время запроса в скрипте.
 */
public class WrongArgumentInRequestInScriptException extends Exception {
    public WrongArgumentInRequestInScriptException(String message) {
        super(message);
    }
}
