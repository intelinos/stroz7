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

import java.util.Scanner;

/**
 * Класс команды insert, которая добавляет в коллекцию элемент с заданным ключом.
 */
public class Insert extends Command {
    private Validator<Integer> keyValidator = new KeyValidator();
    public Insert(){
        this.needScanner=true;
    }

    @Override
    public String getInfo() {
        return "Добавляет в коллекцию элемент с заданным ключом.";
    }

    @Override
    public String getName() {
        return "insert";
    }


    @Override
    public Response execute(){
        try {
            System.out.println("Выполняется команда "+getName());
            int key = Integer.parseInt(getCommandArgument().getKeyArgument());
            if (collectionManager.getCollection().containsKey(key)) throw new WrongArgumentException();
            collectionManager.addToTheCollection(key, getCommandArgument().getOrganizationArgument());
            response = new Response("Вставка элемента с ключом " + key + " завершена.");
            System.out.println("Команда "+getName()+" была выполнена.");
            return response;
        } catch (WrongArgumentException e) {
            ScriptChecker.clearScriptSet();
            response = new Response("Неверное значение ключа, ключ должен быть уникальным!");
            return response;
        }
    }
}
