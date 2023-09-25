package commands;

import Organization.Organization;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import requesters.OrganizationReguester;
import utility.ScriptChecker;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды remove_lower, которая удаляет из коллекции все элементы, меньшие заданного.
 */
public class RemoveLower extends Command{
    public RemoveLower() {
        this.needScanner = true;
    }
    @Override
    public String getInfo() {
        return "Удаляет из коллекции все элементы, меньшие, чем заданный.";
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    /**
     * Проверяет количество аргументов команды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public Command execute(Scanner scanner, String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length != 1) throw new WrongNumberOfArgumentsException();
        try {
            if (ScriptChecker.isScriptInProcess) System.out.println(getName());
            Organization organization = new OrganizationReguester(scanner).getOrganization();
            setCommandArgument(organization);
            return this;
        } catch (WrongArgumentInRequestInScriptException e) {
            System.out.println("Произошла ошибка: "+e.getMessage());
        } return null;
    }
}
