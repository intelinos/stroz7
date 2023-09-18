package commands;

import exceptions.EmptyCommandNameException;
import exceptions.WrongCommandNameException;
import exceptions.WrongNumberOfArgumentsException;
import network.ResponseReceiver;
import utility.ScriptChecker;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import network.RequestSender;

/**
 * Управляет командами.
 */
public class CommandManager {
    private Map<String, Command> commands;
    private RequestSender sender;
    private ResponseReceiver receiver;
    public CommandManager(RequestSender sender, ResponseReceiver receiver) {
        commands = new HashMap<>();
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("insert", new Insert());
        commands.put("update", new Update());
        commands.put("remove", new Remove());
        commands.put("clear", new Clear());
        //commands.put("save", new Save());
        commands.put("execute_script", new ExecuteScript(this, sender, receiver));
        commands.put("exit", new Exit());
        commands.put("remove_greater", new RemoveGreater());
        commands.put("remove_lower", new RemoveLower());
        commands.put("replace_if_greater", new ReplaceIfGreater());
        commands.put("group_counting_by_annual_turnover", new GroupCountingByAnnualTurnover());
        commands.put("print_unique_postal_address", new PrintUniquePostalAddress());
        commands.put("print_field_descending_type", new PrintFieldDescendingType());
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Запрашивает команду у пользователя, проверяет валидность введенной команды и запускает ее выполнение.
     * @param scanner Сканнер, который будет использоваться для считывания команды.
     * @throws NoSuchElementException Если при считывании было обнаружено EOF.
     */
    public Command invokeCommand(Scanner scanner) throws NoSuchElementException {
        if (!ScriptChecker.isScriptInProcess) System.out.print("Введите команду: ");
        try {
            String[] arguments = scanner.nextLine().trim().replaceAll("\\s+"," ").split(" ");
            if (arguments[0].isEmpty() || arguments[0].isBlank()) throw new EmptyCommandNameException();
            if (!commands.containsKey(arguments[0])) throw new WrongCommandNameException();
            Command command = commands.get(arguments[0]);
            if (command.needScanner) {
                if (command.execute(scanner, arguments) == null) {
                    return null;
                }
            }
            else
                if (command.execute(arguments)==null) {
                    return null;
                }
            return command;
        } catch (EmptyCommandNameException e) {
            if(ScriptChecker.isScriptInProcess)System.out.println("Имя команды не может быть пустым. ");
            return null;
        } catch (WrongCommandNameException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка внутри скрипта: несуществующая команда. ");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Ошибка, команды с таким именем не существует. Для получение справки введите help.");
            return null;
        } catch (WrongNumberOfArgumentsException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка внутри скрипта: неверное количество аргументов у команды. ");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Ошибка, неверное количество аргументов.");
            return null;
        }
    }
    /**
     * @return Мапа команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}
