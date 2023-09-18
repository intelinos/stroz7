package validators;

import Organization.Coordinates;

/**
 * Используется для валидации координат.
 */
public class CoordinatesValidator implements Validator<Coordinates> {
    /**
     * Валидирует координаты организации.
     * @param coordinates Координаты организации.
     * @return false - если сам объект Coordinates null, или если метод getX возвращает null. Иначе true.
     */
    @Override
    public boolean validate(Coordinates coordinates) {
        if (coordinates==null) return false;
        if (coordinates.getX()==null) return false;
        return true;
    }
}
