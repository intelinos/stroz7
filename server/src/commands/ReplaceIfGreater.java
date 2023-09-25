package commands;

import Organization.Organization;
import db.DBConnection;
import exceptions.PermisionException;
import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import requesters.OrganizationReguester;
import response.Response;
import utility.ScriptChecker;
import validators.KeyValidator;
import validators.Validator;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды replace_if_greater, которая заменяет элемент коллекции, если новый элемент больше старого.
 */
public class ReplaceIfGreater extends Command{
    private Validator<Integer> keyValidator = new KeyValidator();
    public ReplaceIfGreater() {
        this.needDB=true;
        this.needScanner=true;
    }
    @Override
    public String getInfo() { return " Заменяет значение по ключу, если новое значение больше старого."; }

    @Override
    public String getName() { return "replace_if_greater"; }


    @Override
    public Response execute(DBConnection dbConnection) {
        System.out.println("Выполняется команда "+getName());
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            response = new Response("Коллекция пуста!");
            return response;
        }
        try {
            int key = Integer.parseInt(getCommandArgument().getKeyArgument());
            if (!keyValidator.validate(key) || !collection.containsKey(key)) throw new WrongArgumentException();
            Organization organization = getCommandArgument().getOrganizationArgument();
            String responseMessage;
            if (!dbConnection.checkOrganizationOwner(key, getCommandArgument().getLogin()))
                throw new PermisionException("У вас нет доступа к этой организации.");
            if (doCommand(key, organization, dbConnection)) {
                int id = dbConnection.replaceOrganization(key, getCommandArgument().getOrganizationArgument(), getCommandArgument().getLogin());
                organization.setId(id);
                collectionManager.addToTheCollection(key, organization);
                responseMessage = "Новое значение больше старого, элемент заменен.";
            } else {
                responseMessage = "Новое значение не больше старого, исходный элемент не заменен.";
            }
            response = new Response(responseMessage);
            System.out.println("Команда "+getName()+" была выполнена.");
        }  catch (WrongArgumentException e) {
            ScriptChecker.clearScriptSet();
            response = new Response("Неверное значение ключа, ключ должен существовать в текущей коллекции и быть положительным!");
        } catch (SQLException e){
            response = new Response(e.getMessage());
        }
        return response;
    }

    public boolean doCommand(int key, Organization organization, DBConnection dbConnection) throws SQLException{
        Map<Integer, Organization> collection = collectionManager.getCollection();
        boolean answer = collection.entrySet()
                .stream()
                .filter(entry -> entry.getKey()== key && entry.getValue().compareTo(organization) < 0)
                .findFirst()
                .orElse(null) != null;
        return answer;
    }
}
