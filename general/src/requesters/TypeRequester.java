package requesters;

import Organization.OrganizationType;
import exceptions.WrongArgumentInRequestInScriptException;
import utility.ScriptChecker;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Позволяет запрашивать у пользователя (читать из файла скрипта) тип организации.
 */
public class TypeRequester {
    private Scanner scanner;

    private boolean trueNull=false;

    public TypeRequester(Scanner scanner) {
        this.scanner = scanner;
    }
    /**
     * Запрашивает у пользователя тип организации посредством метода typeRequest, пока не будет введено валидное значение.
     * @return Введенный тип организации.
     * @throws WrongArgumentInRequestInScriptException Если прочитанный тип из файла скрипта оказался невалидным.
     */
    public OrganizationType getType() throws WrongArgumentInRequestInScriptException {
        while(true) {
            try {
                OrganizationType type = typeRequest();
                if (type == null) {
                    if (trueNull) {
                        return null;
                    }
                } else return type;
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод, введите один из типов организации, перечисленных выше.");
                scanner = new Scanner(System.in);
            }
        }
    }
    /**
     * Запрашивает у пользователя (читает из файла скрипта) тип организации и валидирует его.
     * @return Введенный тип организации.
     * @throws NoSuchElementException Если при попытке ввода был обнаружен EOF.
     * @throws WrongArgumentInRequestInScriptException Если прочитанное значение из файла скрипта оказалось невалидным.
     */
    private OrganizationType typeRequest() throws NoSuchElementException, WrongArgumentInRequestInScriptException {
        try {
            if (!ScriptChecker.isScriptInProcess) {
                System.out.print("Введите тип организации: COMMERCIAL, PUBLIC, PRIVATE_LIMITED_COMPANY," +
                        " OPEN_JOINT_STOCK_COMPANY: ");
                scanner = new Scanner(System.in);
            }
            String value = scanner.nextLine().strip();
            if (value.isEmpty()) {
                trueNull=true;
                return null;
            }
            OrganizationType type = OrganizationType.valueOf(value);
            return type;
        } catch (IllegalArgumentException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле type содержит несуществующее значение.");
            else {
                System.out.println("Такого типа организации не существует.");
                return null;
            }
        } catch (NoSuchElementException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле type содержит несуществующее значение.");
            throw e;
        }
    }
}