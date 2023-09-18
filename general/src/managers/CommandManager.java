/*package managers;

import commands.*;
import exceptions.EmptyCommandNameException;
import exceptions.WrongCommandNameException;
import exceptions.WrongNumberOfArgumentsException;
import utility.ScriptChecker;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;



public class CommandManager {
    private CollectionManager collectionManager;
    private Map<String, Command> commands;

    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        commands = new HashMap<>();
        commands.put("help", new Help(this));
        commands.put("info", new Info(collectionManager));
        commands.put("show", new Show(collectionManager));
        commands.put("insert", new Insert(collectionManager));
        commands.put("update", new Update(collectionManager, this));
        commands.put("remove", new Remove(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("save", new Save(collectionManager));
        commands.put("execute_script", new ExecuteScript(this, collectionManager));
        commands.put("exit", new Exit());
        commands.put("remove_greater", new RemoveGreater(collectionManager));
        commands.put("remove_lower", new RemoveLower(collectionManager));
        commands.put("replace_if_greater", new ReplaceIfGreater(collectionManager));
        commands.put("group_counting_by_annual_turnover", new GroupCountingByAnnualTurnover(collectionManager));
        commands.put("print_unique_postal_address", new PrintUniquePostalAddress(collectionManager));
        commands.put("print_field_descending_type", new PrintFieldDescendingType(collectionManager));
    }

*
     * Запрашивает команду у пользователя, проверяет валидность введенной команды и запускает ее выполнение.
     * @param scanner Сканнер, который будет использоваться для считывания команды.
     * @throws NoSuchElementException Если при считывании было обнаружено EOF.


    public void invokeCommand(Scanner scanner) throws NoSuchElementException {
        if (!ScriptChecker.isScriptInProcess) System.out.print("Введите команду: ");
        try {
            String[] arguments = scanner.nextLine().trim().replaceAll("\\s+"," ").split(" ");
            if (arguments[0].isEmpty() || arguments[0].isBlank()) throw new EmptyCommandNameException();
            if (!commands.containsKey(arguments[0])) throw new WrongCommandNameException();
            Command command = commands.get(arguments[0]);
            if (ScriptChecker.isScriptInProcess) System.out.println("Команда "+command.getName()+" запущена.");
            if (command.needScanner)
                command.execute(scanner, arguments);
            else command.execute(arguments);
        } catch (EmptyCommandNameException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: имя команды не может быть пустым. ");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Имя команды не может быть пустым. ");
        } catch (WrongCommandNameException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: несуществующая команда. ");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Ошибка, команды с таким именем не существует. Для получение справки введите help.");
        } catch (WrongNumberOfArgumentsException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: неверное количество аргументов у команды. ");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Ошибка, неверное количество аргументов.");
        }
    }

*
     * @return Мапа команд.


    public Map<String, Command> getCommands() {
        return commands;
    }
}*/
