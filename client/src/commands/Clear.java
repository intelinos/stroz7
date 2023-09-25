package commands;

import exceptions.WrongNumberOfArgumentsException;
import utility.ScriptChecker;


/**
 * Класс команды clear, которая очищает коллекцию.
 */
public class Clear extends Command{
    public Clear(){
        needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Очищает коллекцию.";
    }

    @Override
    public String getName() {
        return "clear";
    }

    /**
     * Проверяет количество аргументов команды clear и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если у команды clear был введен один или несколько аргументов.
     */
    @Override
    public Command execute(String[] arguments, String login, String password) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        setCommandArgument(login, password);
        if (ScriptChecker.isScriptInProcess) System.out.println(getName());
        return this;
    }
}
