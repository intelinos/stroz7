package commands;

import db.DBConnection;
import exceptions.WrongNumberOfArgumentsException;
import response.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс команды clear, которая очищает коллекцию.
 */
public class Clear extends Command{
    public Clear() {
        needDB=true;
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
    public Response execute(DBConnection dbConnection){
        try {
            System.out.println("Выполняется команда " + getName());
            if (collectionManager.getCollection().isEmpty()) {
                response = new Response("Коллекция и так пуста!");
                return response;
            }
            List<Integer> ownerOrganizationsKeys = dbConnection.clearOwnerOrganizations(getCommandArgument().getLogin());
            for(Integer key : ownerOrganizationsKeys) {
                collectionManager.deleteFromTheCollection(key);
            }
            System.out.println("Команда " + getName() + " была выполнена.");
            if(ownerOrganizationsKeys.isEmpty())
                response = new Response("Ваших организаций в коллекции нет.");
            else
                response = new Response("Коллекция была успешно очищена.");
        }catch (SQLException e){
            response=new Response(e.getMessage());
        }
        return response;
    }
}
