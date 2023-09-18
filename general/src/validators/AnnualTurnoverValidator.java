package validators;

/**
 * Используется для валидации годового оборота.
 */
public class AnnualTurnoverValidator implements Validator<Float> {
    /**
     * Валидирует годовой оборот организации.
     * @param annualTurnover Годовой оборот организации.
     * @return true если годовой оборот больше 0 и меньше максимального значения типа float. Иначе - false.
     */
    @Override
    public boolean validate(Float annualTurnover) {
        return annualTurnover > 0 && annualTurnover < Float.MAX_VALUE;
    }
}
