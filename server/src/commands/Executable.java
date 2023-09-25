package commands;

import exceptions.WrongNumberOfArgumentsException;
import response.Response;

import java.util.Scanner;

/**
 * Интерфейс для всех выполняемых команд.
 */
public interface Executable {
    Response execute();
}
