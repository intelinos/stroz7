package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import utility.ScriptChecker;

import java.util.Map;

/**
 * Класс команды show, которая выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 */
public class Show extends Command{
    public Show() {
        this.needScanner=false;
    }
    @Override
    public String getInfo() {
        return "Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.";
    }

    @Override
    public String getName() {
        return "show";
    }
    /**
     * Проверяет количество аргументов команды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public Command execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        if (ScriptChecker.isScriptInProcess) System.out.println(getName());
        return this;
    }
}
