package requesters;

import Organization.Address;
import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import utility.ScriptChecker;
import validators.AddressValidator;
import validators.Validator;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Позволяет запрашивать у пользователя (читать из файла скрипта) адрес организации.
 */
public class AddressRequester {
    Scanner scanner;
    private Address address;

    private Validator<Address> addressValidator = new AddressValidator();
    /**
     * Необходимо, чтобы различать ситуации, когда поле zipCode было намеренно введено пустым и когда оно не прошло валидацию.
     */
    private boolean trueNull = false;
    /**
     * Если это значение - true, тогда мы пропускаем запрос поля zipCode и сразу возвращаем null для адреса.
     */
    private boolean isStreetNull = false;

    public AddressRequester(Scanner scanner) {
        this.scanner = scanner;
    }
    /**
     * Создает объект типа Address с полями, полученными в результате запросов в методах getStreet и getZipCode.
     * @throws WrongArgumentInRequestInScriptException Если при чтении во время выполнения скрипта значения оказались невалидными.
     */
    public void createAddress() throws WrongArgumentInRequestInScriptException {
        address = new Address(getStreet(), getZipCode());
    }
    /**
     * Запрашивает у пользователя улицу, пока не будет введено валидное значение.
     * @return Введенная улица.
     */
    public String getStreet() {
        while(true) {
            try {
                if (!ScriptChecker.isScriptInProcess) System.out.print("Введите улицу: ");
                String street = scanner.nextLine().strip();
                if (street.isEmpty() || street.isBlank()) {
                    isStreetNull = true;
                    return null;
                }
                return street;
            } catch (NoSuchElementException e) {
                if (ScriptChecker.isScriptInProcess)
                    break;
                System.out.println("Неверный ввод в улице!");
                scanner = new Scanner(System.in);
            }
        } return null;
    }
    /**
     * Запрашивает у пользователя почтовый индекс посредством метода zipCodeRequest, пока не будет введено валидное значение.
     * @return Введенный почтовый индекс.
     * @throws WrongArgumentInRequestInScriptException Если прочитанный почтовый индекс из файла скрипта оказалась невалидной.
     */
    private String getZipCode() throws WrongArgumentInRequestInScriptException{
        while(true) {
            try {
                if (isStreetNull) return null;
                String zipCode = zipCodeRequest();
                if (zipCode == null) {
                    if (trueNull) {
                        return null;
                    }
                } else return zipCode;
            } catch (NoSuchElementException e ) {
                System.out.println("Неверный ввод почтовом индексе!");
                scanner = new Scanner(System.in);
            }
        }
    }
    /**
     * Запрашивает у пользователя (читает из файла скрипта) почтовый индекс и валидирует его.
     * @return Введенный почтовый индекс.
     * @throws NoSuchElementException Если при попытке ввода был обнаружен EOF.
     * @throws WrongArgumentInRequestInScriptException Если прочитанное значение из файла скрипта оказалось невалидным.
     */
    private String zipCodeRequest() throws NoSuchElementException, WrongArgumentInRequestInScriptException {
        if (!ScriptChecker.isScriptInProcess) {
            System.out.print("Введите zip-код: ");
            scanner = new Scanner(System.in);
        }
        try {
        String zipCode = scanner.nextLine().strip();
        if (zipCode.isEmpty() || zipCode.isBlank()) {
            trueNull = true;
            return null;
        }
            if(zipCode.length()>20) throw new WrongArgumentException();
            return zipCode;
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Значение поля zip_code должно быть не длиннее 20 символов. ");
            else {
                System.out.println("Ошибка, длина zip_code должна быть не больше 20 символов. ");
                return null;
            }
        } catch (NoSuchElementException e) {
            if(ScriptChecker.isScriptInProcess) {
                //throw new WrongArgumentInRequestInScriptException("");
                trueNull=true;
                return null;
            } throw e;
        }
    }
    /**
     * @return Созданный при помощи createAddress объект Address.
     * @throws WrongArgumentInRequestInScriptException Если при чтении во время выполнения скрипта значения оказались невалидными.
     */
    public Address getAddress() throws WrongArgumentInRequestInScriptException {
        createAddress();
        if (address.getStreet()==null)
            return null;
        return address;
    }
}

