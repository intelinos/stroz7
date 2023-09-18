package commands;

import exceptions.WrongNumberOfArgumentsException;
import response.Response;

/**
 * Класс команды info, которая выводит в стандартный поток вывода информацию о коллекции.
 */
public class Info extends Command{
    public Info() {
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Выводит в стандартный поток вывода информацию о коллекции.";
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public Response execute(){
        System.out.println("Выполняется команда "+getName());
        response = new Response("Информация о коллекции:\n" +
                "Тип коллекции: "+collectionManager.getCollection().getClass().getName() +"\n"+
                "Размер коллекции: "+collectionManager.getCollection().size() +"\n"+
                "Время инициализации: "+collectionManager.getInitiationDate() +"\n"+
                "Время последнего сохранения: "+collectionManager.getLastTimeOfSaving());
        System.out.println("Команда "+getName()+" была выполнена.");
        return response;
    }
}
