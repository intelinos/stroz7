package requesters;

import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import utility.ScriptChecker;
import validators.EmployeesCountValidator;
import validators.Validator;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Позволяет запрашивать у пользователя (читать из файла скрипта) число сотрудников организации.
 */
public class EmployeesCountRequester {
    private Scanner scanner;
    private Validator<Long> employeesCountValidator = new EmployeesCountValidator();

    public EmployeesCountRequester(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Запрашивает у пользователя число сотрудников организации посредством метода employeesCountRequest, пока не будет введено валидное значение.
     * @return Введенное число сотрудников организации.
     * @throws WrongArgumentInRequestInScriptException Если прочитанное число сотрудников из файла скрипта оказалось невалидным.
     */
    public long getEmployeesCount() throws WrongArgumentInRequestInScriptException {
        while(true) {
            try {
                Long employeesCount = employeesCountRequest();
                if (employeesCount != null)
                    return employeesCount;
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод, введите число типа long.");
                scanner = new Scanner(System.in);
            }
        }
    }
    /**
     * Запрашивает у пользователя (читает из файла скрипта) число сотрудников организации и валидирует его.
     * @return Введенное число сотрудников организации.
     * @throws NoSuchElementException Если при попытке ввода был обнаружен EOF.
     * @throws WrongArgumentInRequestInScriptException Если прочитанное значение из файла скрипта оказалось невалидным.
     */
    private Long employeesCountRequest() throws NoSuchElementException, WrongArgumentInRequestInScriptException{
        try {
            if (!ScriptChecker.isScriptInProcess) {
                System.out.print("Введите employeesCount: ");
                scanner = new Scanner(System.in);
            }
            Long employeesCount = Long.parseLong(scanner.nextLine().strip());
            if (!employeesCountValidator.validate(employeesCount)) throw new WrongArgumentException();
            return employeesCount;
        } catch (NumberFormatException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле employees_count должно содержать значение типа long.");
            else {
                System.out.println("Неверный ввод, введите число типа long.");
                return null;
            }
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле employees_count не может быть отрицательным или 0! ");
            else {
                System.out.println("Неверный ввод, число должно быть больше 0.");
                return null;
            }
        } catch (NoSuchElementException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле employees_count должно содержать значение типа long.");
            throw e;
        }
    }
}
