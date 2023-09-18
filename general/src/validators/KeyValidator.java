package validators;

/**
 * Используется для валидации ключей.
 */
public class KeyValidator implements Validator<Integer> {
    /**
     * Валидирует ключ.
     * @param key Ключ.
     * @return true - если ключ больше 0 и меньше максимального значения типа integer. Иначе - false.
     */
    @Override
    public boolean validate(Integer key) {
        return key>0 && key<Integer.MAX_VALUE;
    }
}
