package commands;

import Organization.Organization;
import db.DBConnection;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import requesters.OrganizationReguester;
import response.Response;
import utility.ScriptChecker;

import java.sql.Array;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс команды remove_greater, которая удаляет из коллекции все элементы, превышающие заданный.
 */
public class RemoveGreater extends Command{
    public RemoveGreater() {
        this.needDB=true;
        this.needScanner = true;
    }
    @Override
    public String getInfo() {
        return "Удаляет из коллекции все элементы, превышающие заданный.";
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public Response execute(DBConnection dbConnection){
        try {
            System.out.println("Выполняется команда " + getName());
            Map<Integer, Organization> collection = collectionManager.getCollection();
            if (collection.isEmpty()) {
                response = new Response("Коллекция пуста!");
                return response;
            }
            Organization organization = getCommandArgument().getOrganizationArgument();
            int count = 0;
            Integer[] keys = collectionManager.getCollection().keySet().toArray(new Integer[]{});
            for(Integer key : keys) {
                if (dbConnection.checkOrganizationOwner(key, getCommandArgument().getLogin()) && collection.get(key).compareTo(organization) > 0) {
                    if (dbConnection.removeOrganization(key)) {
                        collectionManager.deleteFromTheCollection(key);
                        count++;
                    }
                }
            }
            String responseMessage;
            if (count == 0) responseMessage = "Не было обнаружено элементов, больших заданного.";
            else responseMessage = "Было удалено " + count + " элементов. ";
            response = new Response(responseMessage);
            System.out.println("Команда " + getName() + " была выполнена.");
        } catch (SQLException e){
            response = new Response(e.getMessage());
        }
        return response;
    }

    /*public int doCommand(Organization organization){
        Map<Integer, Organization> collection = collectionManager.getCollection();
        Map<Integer, Organization> removedElements = collectionManager.getCollection().entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(organization) <= 0)
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        int count = collection.size() - removedElements.size();
        collectionManager.changeCollection(new TreeMap<>(removedElements));
        return count;
    }*/
}
