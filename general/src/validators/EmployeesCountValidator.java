package validators;

/**
 * Используется для валидации количества сотрудников.
 */
public class EmployeesCountValidator implements Validator<Long> {
    /**
     * Валидирует годовой оборот организации.
     * @param employeesCount Количество сотрудников организации.
     * @return true - если количество сотрудников больше 0 и меньше максимального значения типа long. Иначе - false.
     */
    @Override
    public boolean validate(Long employeesCount) {
        return employeesCount > 0 && employeesCount < Long.MAX_VALUE;
    }
}
