package commands;

import exceptions.WrongNumberOfArgumentsException;

import response.Response;
import utility.ScriptChecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import managers.CollectionManager;
public class Save extends Command{
    public Save() {
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

    @Override
    public Response execute(){
        try {
            collectionManager.saveCollectionToFile();
            System.out.println("Коллекция успешно сохранена.");
        } catch (FileNotFoundException e) {
            if (!managers.FileManager.file.canWrite())
                System.out.println("Отсутствуют права на запись в файл.");
        } catch (IOException e) {
            System.out.println("Error: IOException " + e.getMessage());
        } return  null;
    }
}
