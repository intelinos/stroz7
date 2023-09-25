package commands;

import exceptions.WrongNumberOfArgumentsException;
import utility.ScriptChecker;

/**
 * Класс команды exit, которая останавливает работу программы.
 */
public class Exit extends Command{
    public Exit() {
        this.needScanner=false;
    }

    @Override
    public String getInfo() {
        return "Завершает программу (без сохранения в файл).";
    }

    @Override
    public String getName() {
        return "exit";
    }

    /**
     * Проверяет количество введенных аргументов команды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public Command execute(String[] arguments, String login, String password) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        if (ScriptChecker.isScriptInProcess) System.out.println(getName());
        System.out.println("Остановка программы...");
        System.exit(1);
        return this;
    }
}
