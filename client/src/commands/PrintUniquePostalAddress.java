package commands;

import Organization.Address;
import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import utility.ScriptChecker;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс команды print_unique_postal_address, которая выводит уникальные значения поля postalAddress всех элементов в коллекции.
 */
public class PrintUniquePostalAddress extends Command{

    public PrintUniquePostalAddress() {
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Выводит уникальные значения поля postalAddress всех элементов в коллекции.";
    }

    @Override
    public String getName() {
        return "print_unique_postal_address";
    }

    /**
     * Проверяет количество аргументов комманды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public Command execute(String[] arguments, String login, String password) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        setCommandArgument(login, password);
        if (ScriptChecker.isScriptInProcess) System.out.println(getName());
        return this;
    }
}
