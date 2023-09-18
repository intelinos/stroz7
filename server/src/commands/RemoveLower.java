package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import response.Response;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Класс команды remove_lower, которая удаляет из коллекции все элементы, меньшие заданного.
 */
public class RemoveLower extends Command{
    public RemoveLower() {
        this.needScanner = true;
    }
    @Override
    public String getInfo() {
        return "Удаляет из коллекции все элементы, меньшие, чем заданный.";
    }

    @Override
    public String getName() {
        return "remove_lower";
    }


    @Override
    public Response execute(){
        System.out.println("Выполняется команда "+getName());
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            response = new Response("Коллекция пуста!");
            return response;
        }
        Organization organization = getCommandArgument().getOrganizationArgument();
        int count = doCommand(organization);
        String responseMessage;
        if (count == 0) responseMessage = "Не было обнаружено элементов, меньших заданного.";
        else responseMessage = "Было удалено " + count + " элементов. ";
        response = new Response(responseMessage);
        System.out.println("Команда "+getName()+" была выполнена.");
        return response;
    }

    public int doCommand(Organization organization){
        Map<Integer, Organization> collection = collectionManager.getCollection();
        Map<Integer, Organization> removedElements = collectionManager.getCollection().entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(organization) >= 0)
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        int count = collection.size() - removedElements.size();
        collectionManager.changeCollection(new TreeMap<>(removedElements));
        return count;
    }
}
