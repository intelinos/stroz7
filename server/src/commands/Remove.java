package commands;

import exceptions.WrongArgumentException;
import exceptions.WrongNumberOfArgumentsException;
import response.Response;
import utility.ScriptChecker;
import validators.KeyValidator;
import validators.Validator;

/**
 * Класс команды remove, которая удаляет из коллекции элемент с заданным ключом.
 */
public class Remove extends Command {
    private Validator<Integer> keyValidator = new KeyValidator();

    public Remove() {
        this.needScanner = false;
    }

    @Override
    public String getInfo() {
        return "Удаляет из коллекции элемент с заданным ключом.";
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public Response execute(){
        System.out.println("Выполняется команда "+getName());
        if (collectionManager.getCollection().isEmpty()) {
            response = new Response("Коллекция пуста!");
            return response;
        }
        int key = Integer.parseInt(getCommandArgument().getKeyArgument());
        try {
            if (!collectionManager.getCollection().containsKey(key))
                throw new WrongArgumentException();
            collectionManager.deleteFromTheCollection(key);
            response = new Response("Элемент в ключом " + key + " был удален.");
            System.out.println("Команда "+getName()+" была выполнена.");
            return response;
        } catch (WrongArgumentException e) {
            ScriptChecker.clearScriptSet();
            response = new Response("Неверное значение ключа: ключ должен существовать в текущей коллекции.");
            return response;
        }
    }
}
