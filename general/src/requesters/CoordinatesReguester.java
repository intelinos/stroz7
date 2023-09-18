package requesters;

import Organization.Coordinates;
import exceptions.WrongArgumentInRequestInScriptException;
import utility.ScriptChecker;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Позволяет запрашивать у пользователя (читать из файла скрипта) координаты организации.
 */
public class CoordinatesReguester {
    private Coordinates coordinates;
    private Scanner scanner;
    public CoordinatesReguester(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Создает объект типа Coordinates с координатами, полученными в результате запросов в методах getX и getY.
     * @throws WrongArgumentInRequestInScriptException Если при чтении во время выполнения скрипта значения оказались невалидными.
     */
    private void createCoordinates() throws WrongArgumentInRequestInScriptException
    {
        coordinates = new Coordinates(getX(), getY());
    }
    /**
     * Запрашивает у пользователя координату X посредством метода requestX, пока не будет введено валидное значение.
     * @return Введенная координата X.
     * @throws WrongArgumentInRequestInScriptException Если прочитанная координата X из файла скрипта оказался невалидным.
     */
    private Long getX () throws WrongArgumentInRequestInScriptException{
        while(true) {
            try {
                Long X = requestX();
                if (X != null)
                    return X;
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод: введите число типа long.");
                scanner = new Scanner(System.in);
            }
        }
    }
    /**
     * Запрашивает у пользователя (читает из файла скрипта) координату X и валидирует его.
     * @return Введенная координата X.
     * @throws NoSuchElementException Если при попытке ввода был обнаружен EOF.
     * @throws WrongArgumentInRequestInScriptException Если прочитанное значение из файла скрипта оказалось невалидным.
     */
    private Long requestX() throws NoSuchElementException, WrongArgumentInRequestInScriptException {
            try {
                if (!ScriptChecker.isScriptInProcess) {
                    System.out.print("Введите Х: ");
                    scanner = new Scanner(System.in);
                }
                Long X = Long.parseLong(scanner.nextLine().strip());
                return X;
            } catch (NumberFormatException e) {
                if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле X должно содержать значение типа long. ");
                else {
                    System.out.println("Неверный ввод: введите число типа long.");
                    return null;
                }
            } catch (NoSuchElementException e) {
                if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле X должно содержать значение типа long. ");
                throw e;
            }
    }
    /**
     * Запрашивает у пользователя координату Y посредством метода requestY, пока не будет введено валидное значение.
     * @return Введенная координата Y.
     * @throws WrongArgumentInRequestInScriptException Если прочитанная координата Y из файла скрипта оказался невалидным.
     */
    private double getY()  throws WrongArgumentInRequestInScriptException{
        while(true) {
            try {
                Double Y = requestY();
                if (Y != null)
                    return Y;
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод, введите число типа float.");
                scanner = new Scanner(System.in);
            }
        }
    }
    /**
     * Запрашивает у пользователя (читает из файла скрипта) координату Y и валидирует его.
     * @return Введенная координата Y.
     * @throws NoSuchElementException Если при попытке ввода был обнаружен EOF.
     * @throws WrongArgumentInRequestInScriptException Если прочитанное значение из файла скрипта оказалось невалидным.
     */
    private Double requestY() throws NoSuchElementException, WrongArgumentInRequestInScriptException {
        try {
            if (!ScriptChecker.isScriptInProcess) {
                System.out.print("Введите Y: ");
                scanner = new Scanner(System.in);
            }
            String stringY = scanner.nextLine().trim();
            if (stringY.isEmpty())
                return 0d;
            double Y = Double.parseDouble(stringY);
            return Y;
        } catch (NumberFormatException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле Y должно содержать значение типа double. ");
            else {
                System.out.println("Неверный ввод, введите число типа double.");
                return null;
            }
        } catch (NoSuchElementException e) {
            if (ScriptChecker.isScriptInProcess) throw new WrongArgumentInRequestInScriptException("Поле Y должно содержать значение типа double. ");
            throw e;
        }
    }

    /**
     * @return Созданный при помощи createCoordinates объект Coordinates.
     * @throws WrongArgumentInRequestInScriptException Если при чтении во время выполнения скрипта координаты оказались невалидными.
     */
    public Coordinates getCoordinates() throws WrongArgumentInRequestInScriptException{
        createCoordinates();
        return coordinates;
    }
}
