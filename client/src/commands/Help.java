package commands;

import exceptions.WrongNumberOfArgumentsException;
import utility.ScriptChecker;


import java.util.Map;

/**
 * Класс команды help, которая выводит справку о доступных командах.
 */
public class Help extends Command {


    @Override
    public String getInfo() {
        return "Выводит справку о доступных командах. ";
    }

    @Override
    public String getName() {
        return "help";
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
