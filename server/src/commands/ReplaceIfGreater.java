package commands;

import Organization.Organization;
import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import requesters.OrganizationReguester;
import response.Response;
import utility.ScriptChecker;
import validators.KeyValidator;
import validators.Validator;

import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды replace_if_greater, которая заменяет элемент коллекции, если новый элемент больше старого.
 */
public class ReplaceIfGreater extends Command{
    private Validator<Integer> keyValidator = new KeyValidator();
    public ReplaceIfGreater() {
        this.needScanner=true;
    }
    @Override
    public String getInfo() { return " Заменяет значение по ключу, если новое значение больше старого."; }

    @Override
    public String getName() { return "replace_if_greater"; }


    @Override
    public Response execute() {
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
            if (doCommand(key, organization)) {
                responseMessage = "Новое значение больше старого, элемент заменен.";
            } else {
                responseMessage = "Новое значение не больше старого, исходный элемент не заменен.";/*+"\n"
                                    +"Элемент, который был введен: "+"\n"
                                    + organization;*/
            }
            response = new Response(responseMessage);
            System.out.println("Команда "+getName()+" была выполнена.");
        }  catch (WrongArgumentException e) {
            ScriptChecker.clearScriptSet();
            response = new Response("Неверное значение ключа, ключ должен существовать в текущей коллекции и быть положительным!");
        } return response;
    }

    public boolean doCommand(int key, Organization organization){
        Map<Integer, Organization> collection = collectionManager.getCollection();
        return collection.entrySet()
                .stream()
                .filter(entry -> entry.getKey()== key && entry.getValue().compareTo(organization) < 0)
                .map(entry -> collection.compute(key, (k, v) -> organization))
                .findFirst()
                .orElse(null) != null;
    }
}
