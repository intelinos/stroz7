package commands;

import Organization.Organization;
import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import requesters.OrganizationReguester;
import utility.ScriptChecker;
import validators.KeyValidator;
import validators.Validator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды update, которая обновляет значение элемента коллекции, id которого равен заданному.
 */
public class Update extends Command{
    private Validator<Integer> keyValidator = new KeyValidator();
    public Update() {
        this.needScanner = true;
    }
    @Override
    public String getInfo() {
        return "Обновляет значение элемента коллекции, id которого равен заданному.";
    }

    @Override
    public String getName() {
        return "update";
    }

    /**
     * Проверяет количество аргументов команды и выполняет ее.
     * @param scanner Сканнер, который будет использоваться при выполнении команды. Выполнение команды прерывается, если введен id, не являющийся положительным числом типа int, а также если id не содержится в коллекции.
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
                System.out.println("Произошла ошибка внутри скрипта: id должен быть целым числом типа int.");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение id, необходимо ввести целое число типа int.");
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка внутри скрипта: id должен быть положительным!");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение id, id должен быть положительным!");
        } catch (WrongArgumentInRequestInScriptException e) {
            System.out.println("Произошла ошибка: "+e.getMessage());
        } return null;
    }
}
