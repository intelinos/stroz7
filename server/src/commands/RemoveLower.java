package commands;

import Organization.Organization;
import db.DBConnection;
import exceptions.WrongNumberOfArgumentsException;
import response.Response;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Класс команды remove_lower, которая удаляет из коллекции все элементы, меньшие заданного.
 */
public class RemoveLower extends Command {
    public RemoveLower() {
        this.needDB=true;
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
    public Response execute(DBConnection dbConnection) {
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
                if (dbConnection.checkOrganizationOwner(key, getCommandArgument().getLogin()) && collection.get(key).compareTo(organization) < 0) {
                    if (dbConnection.removeOrganization(key)) {
                        collectionManager.deleteFromTheCollection(key);
                        count++;
                    }
                }
            }
            String responseMessage;
            if (count == 0) responseMessage = "Не было обнаружено элементов, меньших заданного.";
            else responseMessage = "Было удалено " + count + " элементов. ";
            response = new Response(responseMessage);
            System.out.println("Команда " + getName() + " была выполнена.");
        } catch (SQLException e) {
            response = new Response(e.getMessage());
        }
        return response;
    }
}
