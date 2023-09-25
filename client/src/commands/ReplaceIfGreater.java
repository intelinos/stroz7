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

import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды replace_if_greater, которая заменяет элемент коллекции, если новый элемент больше старого.
 */
public class ReplaceIfGreater extends Command{
    private Validator<Integer> keyValidator = new KeyValidator();
    public ReplaceIfGreater() {
        this.needScanner=true;
    }
    @Override
    public String getInfo() { return " Заменяет значение по ключу, если новое значение больше старого."; }

    @Override
    public String getName() { return "replace_if_greater"; }

    /**
     * Проверяет количество аргументов команды и выполняет ее. Выполнение команды прерывается, если введен ключ, не являющийся положительным числом типа int, а также если ключ не содержится в коллекции.
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
            System.out.println("Произошла ошибка: "+e.getMessage());
        } return null;
    }
}