package commands;

import exceptions.WrongNumberOfArgumentsException;
import response.Response;


/**
 * Класс команды clear, которая очищает коллекцию.
 */
public class Clear extends Command{
    public Clear() {
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

    @Override
    public Response execute(){
        System.out.println("Выполняется команда "+getName());
        if (collectionManager.getCollection().isEmpty()) {
            response = new Response("Коллекция и так пуста!");
            return response;
        }
        collectionManager.getCollection().clear();
        System.out.println("Команда "+getName()+" была выполнена.");
        response = new Response("Коллекция была успешно очищена.");
        return response;
    }
}
