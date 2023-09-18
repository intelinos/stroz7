package commands;

import exceptions.WrongNumberOfArgumentsException;

import java.util.Scanner;

/**
 * Интерфейс для всех выполняемых команд.
 */
public interface Executable {
    /**
     * Запускает команду, которая не требует сканер.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если было введено неверное количество аргументов команды.
     */
    Command execute(String arguments[]) throws WrongNumberOfArgumentsException;
    /**
     * Запускает команду, которая требует сканер.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если было введено неверное количество аргументов команды.
     */
    Command execute(Scanner scanner, String arguments[]) throws  WrongNumberOfArgumentsException;
}
