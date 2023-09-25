package commands;

import exceptions.WrongNumberOfArgumentsException;

import java.util.Scanner;

/**
 * Интерфейс для всех выполняемых команд.
 */
public interface Executable {

    Command execute(String login, String password);
    Command execute(String arguments[], String login, String password) throws WrongNumberOfArgumentsException;
    /**
     * Запускает команду, которая требует сканер.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если было введено неверное количество аргументов команды.
     */
    Command execute(Scanner scanner, String arguments[], String login, String password) throws  WrongNumberOfArgumentsException;
}
