package validators;

import exceptions.WrongDeserializationError;

/**
 * Интерфейс для валидации значений, которые могут выкинуть исключение WrongDeserializationError.
 * @param <T> Валидируемое значение.
 */
public interface ValidatorWithException<T> {
    /**
     * Валидирует введенное значение, с возможностью выкидывания исключения WrongDeserializationError.
     * @param value Валидируемое значение.
     * @return true - если значение прошло валидацию (валидное), если не прошло - выкидывается исключение WrongDeserializationError.
     */
    public boolean validate(T value) throws WrongDeserializationError;
}
