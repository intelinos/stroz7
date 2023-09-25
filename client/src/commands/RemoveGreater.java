package commands;

import Organization.Organization;
import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import requesters.OrganizationReguester;
import utility.ScriptChecker;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды remove_greater, которая удаляет из коллекции все элементы, превышающие заданный.
 */
public class RemoveGreater extends Command{
    public RemoveGreater() {
        this.needScanner = true;
    }
    @Override
    public String getInfo() {
        return "Удаляет из коллекции все элементы, превышающие заданный.";
    }

    @Override
    public String getName() {
        return "remove_greater";
    }
    /**
     * Проверяет количество аргументов команды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public Command execute(Scanner scanner, String[] arguments, String login, String password) throws WrongNumberOfArgumentsException {
        if (arguments.length != 1) throw new WrongNumberOfArgumentsException();
        try {
            if (ScriptChecker.isScriptInProcess) System.out.println(getName());
            Organization organization = new OrganizationReguester(scanner).getOrganization();
            setCommandArgument(organization, login, password);
            return this;
        } catch (WrongArgumentInRequestInScriptException e) {
            System.out.println("Произошла ошибка: "+e.getMessage());
        } return null;
    }
}
