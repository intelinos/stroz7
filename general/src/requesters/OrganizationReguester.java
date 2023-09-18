package requesters;

import Organization.Address;
import Organization.Coordinates;
import Organization.Organization;
import Organization.OrganizationType;
import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import utility.ScriptChecker;
import validators.NameValidator;
import validators.Validator;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Позволяет запрашивать у пользователя (или читать из файла скрипта) элементы коллекции.
 */
public class OrganizationReguester {
    private Organization organization;
    private Scanner scanner;
    private AddressRequester addressRequester;
    private CoordinatesReguester coordinatesReguester;
    private AnnualTurnoverRequester annualTurnoverRequester;
    private EmployeesCountRequester employeesCountRequester;
    private TypeRequester typeRequester;
    private Validator<String> nameValidator = new NameValidator();

    public OrganizationReguester(Scanner scanner) {
        this.scanner = scanner;
    }
    /**
     * Запрашивает все поля объекта класса Organization и создает этот объект. При некорректном вводе выводится сообщение об ошибке и необходимое поле запрашивается еще раз.
     * @throws WrongArgumentInRequestInScriptException Если был обнаружен некорректный ввод при выполнении скрипта.
     */
    private void createOrganization() throws WrongArgumentInRequestInScriptException {
        String name = getName();
        coordinatesReguester = new CoordinatesReguester(scanner);
        Coordinates coordinates = coordinatesReguester.getCoordinates();
        annualTurnoverRequester = new AnnualTurnoverRequester(scanner);
        float annualTurnover = annualTurnoverRequester.getAnnualTurnover();
        employeesCountRequester = new EmployeesCountRequester(scanner);
        long employeesCount = employeesCountRequester.getEmployeesCount();
        typeRequester = new TypeRequester(scanner);
        OrganizationType type = typeRequester.getType();
        addressRequester = new AddressRequester(scanner);
        Address address = addressRequester.getAddress();

        organization = new Organization(name, coordinates, annualTurnover,
                                        employeesCount, type, address);
    }

    /**
     * @return Объект, который был создан в результате запросов в методе createOrganization.
     * @throws WrongArgumentInRequestInScriptException Если при создании объекта был обнаружен некорректный ввод при выполнении скрипта.
     */
    public Organization getOrganization() throws WrongArgumentInRequestInScriptException {
        createOrganization();
        return organization;
    }

    /**
     * Запрашивает у пользователя имя организации посредством метода nameRequest, пока не будет введено валидное имя.
     * @return Введенное имя организации.
     * @throws WrongArgumentInRequestInScriptException Если прочитанное имя из файла скрипта оказалось невалидным.
     */
    private String getName() throws WrongArgumentInRequestInScriptException {
        while(true) {
            try {
                String name = nameRequest();
                if (name != null) return name;
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод в имени!");
                scanner = new Scanner(System.in);
            }
        }
    }

    /**
     * Запрашивает у пользователя (читает из файла скрипта) имя организации и валидирует его.
     * @return Введенное имя организации.
     * @throws NoSuchElementException Если при попытке ввода был обнаружен EOF.
     * @throws WrongArgumentInRequestInScriptException Если прочитанное имя из файла скрипта оказалось невалидным.
     */
    private String nameRequest() throws NoSuchElementException, WrongArgumentInRequestInScriptException {
        try {
            if (!ScriptChecker.isScriptInProcess) {
                System.out.print("Введите имя: ");
                scanner = new Scanner(System.in);
            }
            String name = scanner.nextLine().strip();
            if (!nameValidator.validate(name)) throw new WrongArgumentException();
            return name;
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Имя не может быть null, или пустым.");
            else {
                System.out.println("Имя не может быть null, или пустым.");
                return null;
            }
        } catch (NoSuchElementException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Имя не может быть null, или пустым.");
            throw e;
        }
    }
}
