package exceptions;

/**
 * Исключение, возникающее при обнаружении невалидных полей при загрузке коллекции из файла в формате JSON.
 */
public class WrongDeserializationError extends Exception{
    public WrongDeserializationError(String message) {
        super(message);
    }
}
