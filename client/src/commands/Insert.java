package commands;

import Organization.Organization;
import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import managers.*;
import requesters.OrganizationReguester;
import utility.ScriptChecker;
import validators.KeyValidator;
import validators.Validator;

import java.util.Scanner;

/**
 * Класс команды insert, которая добавляет в коллекцию элемент с заданным ключом.
 */
public class Insert extends Command {
    private Validator<Integer> keyValidator = new KeyValidator();
    public Insert() {
        this.needScanner=true;
    }

    @Override
    public String getInfo() {
        return "Добавляет в коллекцию элемент с заданным ключом.";
    }

    @Override
    public String getName() {
        return "insert";
    }

    /**
     * Проверяет количество аргументов команды и выполняет ее. Выполнение команды прерывается, если введен ключ, не являющийся положительным числом типа int, а также если ключ уже содержится в коллекции.
     * @param scanner Сканнер, который будет использоваться при выполнении команды.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если количество аргументов команды не равно одному.
     */
    @Override
    public Command execute(Scanner scanner, String[] arguments, String login, String password) throws WrongNumberOfArgumentsException {
        if (arguments.length != 2) throw new WrongNumberOfArgumentsException();
        try {
            if (ScriptChecker.isScriptInProcess) System.out.println(getName()+ " " + arguments[1].strip());
            int key = Integer.parseInt(arguments[1].strip());
            if (!keyValidator.validate(key)) throw new WrongArgumentException();
            Organization organization = new OrganizationReguester(scanner).getOrganization();
            setCommandArgument(key+"", organization, login, password);
            return this;
        } catch (NumberFormatException  e){
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка внутри скрипта: ключ должен быть целым числом типа int.");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение ключа, необходимо ввести целое число типа int.");
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка внутри скрипта: ключ должен быть положительным!");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение ключа, ключ должен быть положительным!");
        } catch (WrongArgumentInRequestInScriptException e) {
            System.out.println("Произошла ошибка внутри скрипта: "+e.getMessage());
            ScriptChecker.clearScriptSet();
        } return null;
    }
}
