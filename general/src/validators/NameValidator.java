package validators;

/**
 * Используется для валидации имени.
 */
public class NameValidator implements Validator<String> {
    /**
     * Валидирует имя организации.
     * @param name Имя организации.
     * @return false - если имя пустое либо содержит только пробелы. Иначе - true.
     */
    @Override
    public boolean validate(String name) {
        return !(name.isEmpty() || name.isBlank());
    }

}
