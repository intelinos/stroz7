package validators;

import Organization.Address;

/**
 * Реализует валидацию адреса.
 */
public class AddressValidator implements Validator<Address> {
    /**
     * Валидирует адрес организации.
     * @param address Адрес организации.
     * @return Если объект Address null, либо введенные значения валидные - true, если введенные значения невалидные - false.
     */
    @Override
    public boolean validate(Address address) {
        if (address==null)
            return true;
        if (address.getStreet()==null) {
            if (address.getZipCode() == null) return true;
            return false;
        }
        if (address.getStreet().isBlank() || address.getStreet().isEmpty()) return false;
        else if (address.getZipCode()==null || address.getZipCode().length()<=20) return true;
        return false;
    }
}
