package requesters;

import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import utility.ScriptChecker;
import validators.AnnualTurnoverValidator;
import validators.Validator;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Позволяет запрашивать у пользователя (читать из файла скрипта) годовой оборот организации.
 */
public class AnnualTurnoverRequester {
    private Scanner scanner;
    private Validator<Float> annualTurnoverValidator = new AnnualTurnoverValidator();
    public AnnualTurnoverRequester(Scanner scanner) {
        this.scanner = scanner;
    }
    /**
     * Запрашивает у пользователя годовой оборот организации посредством метода annualTurnoverRequest, пока не будет введено валидное значение.
     * @return Введенный годовой оборот организации.
     * @throws WrongArgumentInRequestInScriptException Если прочитанный годовой оборот из файла скрипта оказался невалидным.
     */
    public Float getAnnualTurnover() throws WrongArgumentInRequestInScriptException {
        while(true) {
            try {
            Float annualTurnover = annualTurnoverRequest();
            if (annualTurnover!=null)
                return annualTurnover;
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод, введите число типа float.");
                scanner = new Scanner(System.in);
            }
        }
    }
    /**
     * Запрашивает у пользователя (читает из файла скрипта) годовой оборот организации и валидирует его.
     * @return Введенный годовой оборот организации.
     * @throws NoSuchElementException Если при попытке ввода был обнаружен EOF.
     * @throws WrongArgumentInRequestInScriptException Если прочитанное значение из файла скрипта оказалось невалидным.
     */
    private Float annualTurnoverRequest() throws NoSuchElementException, WrongArgumentInRequestInScriptException {
        try {
            if (!ScriptChecker.isScriptInProcess) {
                System.out.print("Введите annualTurnover: ");
                scanner = new Scanner(System.in);
            }
            Float annualTurnover = Float.parseFloat(scanner.nextLine().strip());
            if (!annualTurnoverValidator.validate(annualTurnover)) throw new WrongArgumentException();
            return annualTurnover;
        } catch (NumberFormatException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле annual_turnover должно содержать значение типа float. ");
            else {
                System.out.println("Неверный ввод, введите число типа float. ");
                return null;
            }
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле annual_turnover должно не может быть отрицательным или 0. ");
            else {
                System.out.println("Неверный ввод, число должно быть больше 0. ");
                return null;
            }
        } catch (NoSuchElementException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле annual_turnover должно не может быть отрицательным или 0. ");
            throw e;
        }
    }
}
