package validators;

/**
 * Интерфейс для валидации значений.
 * @param <T> Валидируемое значение.
 */
public interface Validator<T> {
    /**
     * Валидирует введенное значение.
     * @param value Валидируемое значение.
     * @return true - если значение прошло валидацию (валидное), false - если значение не прошло валидацию (невалидное).
     */
    public boolean validate(T value);
}
