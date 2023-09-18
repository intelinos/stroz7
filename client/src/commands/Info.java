package commands;

import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import utility.ScriptChecker;

/**
 * Класс команды info, которая выводит в стандартный поток вывода информацию о коллекции.
 */
public class Info extends Command{
    public Info() {
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Выводит в стандартный поток вывода информацию о коллекции.";
    }

    @Override
    public String getName() {
        return "info";
    }
    /**
     * Проверяет количество аргументов комманды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public Command execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length!=1) throw new WrongNumberOfArgumentsException();
        if (ScriptChecker.isScriptInProcess) System.out.println(getName());
        return this;
    }
}
