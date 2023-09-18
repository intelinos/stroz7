package commands;

import Organization.Organization;
import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import requesters.OrganizationReguester;
import response.Response;
import utility.ScriptChecker;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды update, которая обновляет значение элемента коллекции, id которого равен заданному.
 */
public class Update extends Command{
    Scanner scanner;
    public Update() {
        this.needScanner = true;
    }
    @Override
    public String getInfo() {
        return "Обновляет значение элемента коллекции, id которого равен заданному.";
    }

    @Override
    public String getName() {
        return "update";
    }


    @Override
    public Response execute() {
        System.out.println("Выполняется команда "+getName());
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            response = new Response("Коллекция пуста!");
            return response;
        }
        try {
            Integer id = Integer.parseInt(getCommandArgument().getKeyArgument());
            /*int key=0;
            for (Map.Entry<Integer, Organization> pair : collection.entrySet()) {
                if (id.equals(pair.getValue().getId()))
                    key = pair.getKey();
            }*/
          //  if (key == 0) throw new WrongArgumentException();
            Organization organization = getCommandArgument().getOrganizationArgument();
            if (!doCommand(id, organization)) throw new WrongArgumentException();
            String responseMessage = "Обновление элемента с id = "+id+" завершено.";
            response = new Response(responseMessage);
            System.out.println("Команда "+getName()+" была выполнена.");
        } catch (WrongArgumentException e) {
            ScriptChecker.clearScriptSet();
            response = new Response("Недопустимое значение id, элемента с таким id не существует.");
        } return response;
    }

    public boolean doCommand(int id, Organization organization){
        Map<Integer, Organization> collection = collectionManager.getCollection();
        LocalDateTime creationDate = collection.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getId()==id)
                .findFirst()
                .get()
                .getValue()
                .getCreationDate();
        return collection.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getId()==id)
                .map(entry -> collection.compute(entry.getKey(), (k, v) -> organization))
                .peek(org -> {org.setId(id); org.setCreationDate(creationDate);})
                .findFirst()
                .orElse(null) != null;
    }
}
