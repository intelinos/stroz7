package commands;

import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import response.Response;

import java.io.IOException;

/**
 * Класс команды exit, которая останавливает работу программы.
 */
public class Exit extends Command{
    public Exit(){
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

    @Override
    public Response execute(){
        try {
            System.out.println("Сохранение коллекции...");
            collectionManager.saveCollectionToFile();
            System.out.println("Остановка программы...");
            System.exit(1);
        } catch (IOException e){
            System.out.println(e);
        }

        return null;
    }
}
