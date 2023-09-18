/*
package commands;

import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.FileManager;
import utility.ScriptChecker;

import java.io.FileNotFoundException;
import java.io.IOException;

*/
/**
 * Класс команды save, которая сохраняет коллекцию в файл.
 *//*

public class Save extends Command{
    CollectionManager collectionManager;
    public Save(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Сохраняет коллекцию в файл.";
    }

    @Override
    public String getName() {
        return "save";
    }

    */
/**
     * Проверяет количество аргументов команды и выполняет ее. Сохранения не происходит, если отсутствуют права для записи в файл.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     *//*

    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        try {
            collectionManager.saveCollectionToFile();
            System.out.println("Коллекция успешно сохранена.");
        } catch (FileNotFoundException e) {
            if (!FileManager.file.canWrite())
                if (ScriptChecker.isScriptInProcess) {
                    System.out.println("Произошла ошибка: Отсутствуют права на запись в файл.");
                    ScriptChecker.clearScriptSet();
                } else System.out.println("Отсутствуют права на запись в файл.");
        } catch (IOException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: IOException " + e.getMessage());
                ScriptChecker.clearScriptSet();
            } else System.out.println("Error: IOException " + e.getMessage());
        }
    }
}
*/
