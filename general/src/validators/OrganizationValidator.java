package validators;

import Organization.Address;
import Organization.Coordinates;
import Organization.Organization;
import exceptions.WrongDeserializationError;

/**
 * Используется для валидации организации.
 */
public class OrganizationValidator implements ValidatorWithException<Organization> {
    private Validator<Address> addressValidator = new AddressValidator();
    private Validator<Coordinates> coordinatesValidator = new CoordinatesValidator();
    private Validator<Integer> idValidator = new KeyValidator();
    private Validator<Long> employeesCountValidator = new EmployeesCountValidator();
    private Validator<Float> annualTurnoverValidator = new AnnualTurnoverValidator();
    private Validator<String> nameValidator = new NameValidator();

    /**
     * Валидирует организацию.
     * @param organization
     * @return true - если все поля валидные.
     * @throws WrongDeserializationError Если были обнаружены невалидные поля.
     */
    @Override
    public boolean validate(Organization organization) throws WrongDeserializationError {
        if (organization.getId() == null || !idValidator.validate(organization.getId()))
            throw new WrongDeserializationError("Значение id не может быть null, а также должно быть положительным числом типа int.");
        if (organization.getName() == null || !nameValidator.validate(organization.getName()))
            throw new WrongDeserializationError("Имя не может быть пустым или null.");
        if (!coordinatesValidator.validate(organization.getCoordinates()))
            throw new WrongDeserializationError("Поле coordinates, а также X не может быть null.");
        if(organization.getCreationDate()==null)
            throw new WrongDeserializationError("Значение creation_date не может быть null, а также должно быть введено в формате: YYYY-MM-DDTHH-MM-SS");
        if (!annualTurnoverValidator.validate(organization.getAnnualTurnover()))
            throw new WrongDeserializationError("Значение annual_turnover не может быть null, а также должно быть положительным числом типа float.");
        if (!employeesCountValidator.validate(organization.getEmployeesCount()))
            throw new WrongDeserializationError("Значение employees_count не может быть null, а также должно быть положительным числом типа long.");
        if (!addressValidator.validate(organization.getPostalAddress()))
            throw new WrongDeserializationError("Поле street не может быть пустым или null, а поле zip_code не должно быть длиннее 20 символов.");
        return true;
    }
}
