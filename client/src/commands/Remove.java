package commands;

import Organization.Organization;
import exceptions.WrongArgumentException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import requesters.OrganizationReguester;
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

    /**
     * Проверяет количество аргументов команды и выполняет ее. Выполнение команды прерывается, если введен ключ, не являющийся положительным числом типа int, а также если ключ не содержится в коллекции.
     *
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если количество аргументов команды не равно одному.
     */
    @Override
    public Command execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length != 2) throw new WrongNumberOfArgumentsException();
        try {
            if (ScriptChecker.isScriptInProcess) System.out.println(getName()+ " " + arguments[1].strip());
            int key = Integer.parseInt(arguments[1].strip());
            if (!keyValidator.validate(key)) throw new WrongArgumentException();
            setCommandArgument(key+"");
            return this;
        } catch (NumberFormatException  e){
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка внутри скрипта: ключ должен быть целым числом типа int.");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение ключа, необходимо ввести целое число типа int.");
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка внутри скрипта: ключ должен быть положительным!");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение ключа, ключ должен быть положительным!");
        }
        return null;
    }
}
