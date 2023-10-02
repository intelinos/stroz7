package commands;

import exceptions.EmptyCommandNameException;
import exceptions.WrongCommandNameException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import request.Request;
import response.Response;
import utility.ScriptChecker;
import utility.ScriptHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Управляет командами.
 */
public class CommandManager {
    private CollectionManager collectionManager;
    private Map<String, Command> commands;
    private ScriptHandler scriptHandler;
    public CommandManager() {
        commands = new HashMap<>();
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("insert", new Insert());
        commands.put("update", new Update());
        commands.put("remove", new Remove());
        commands.put("clear", new Clear());
        //commands.put("save", new Save());
        commands.put("login_user", new LoginUser());
        commands.put("register_user", new RegisterUser());
        commands.put("execute_script", new ExecuteScript());
        commands.put("exit", new Exit());
        commands.put("remove_greater", new RemoveGreater());
        commands.put("remove_lower", new RemoveLower());
        commands.put("replace_if_greater", new ReplaceIfGreater());
        commands.put("group_counting_by_annual_turnover", new GroupCountingByAnnualTurnover());
        commands.put("print_unique_postal_address", new PrintUniquePostalAddress());
        commands.put("print_field_descending_type", new PrintFieldDescendingType());
    }

    /**
     * Запрашивает команду у пользователя, проверяет валидность введенной команды и запускает ее выполнение.
     * @param scanner Сканнер, который будет использоваться для считывания команды.
     * @throws NoSuchElementException Если при считывании было обнаружено EOF.
     */
    public Response invokeCommand(Scanner scanner) throws NoSuchElementException {
        if (!ScriptChecker.isScriptInProcess) System.out.print("Введите команду: ");
        Response response;
        try {
            String[] arguments = scanner.nextLine().trim().replaceAll("\\s+"," ").split(" ");
            if (arguments[0].isEmpty() || arguments[0].isBlank()) throw new EmptyCommandNameException();
            if (!commands.containsKey(arguments[0])) throw new WrongCommandNameException();
            Command command = commands.get(arguments[0]);
            command.setCollectionManager(collectionManager);
            //if(command.getName().equals("execute_script")) {
            //    ((ExecuteScript) command).setScriptHandler(scriptHandler);
            //   command.setCommandArgument(arguments[1]);
            //}
            if (ScriptChecker.isScriptInProcess) System.out.println("Команда "+command.getName()+" запущена.");
            response = command.execute();
            return response;
        } catch (EmptyCommandNameException e) {
            if (ScriptChecker.isScriptInProcess) {
                response = new Response("Произошла ошибка: имя команды не может быть пустым. ");
                ScriptChecker.clearScriptSet();
            } else {
                response = new Response("Имя команды не может быть пустым. ");
            }
            return response;
        } catch (WrongCommandNameException e) {
            if (ScriptChecker.isScriptInProcess) {
                response = new Response("Произошла ошибка: несуществующая команда. ");
                ScriptChecker.clearScriptSet();
            } else {
                response = new Response("Ошибка, команды с таким именем не существует. Для получение справки введите help.");
            }
            return response;
        }
    }
    /**
     * @return Мапа команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
    public void setCollectionManager(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    public void setScriptHandler(ScriptHandler scriptHandler){this.scriptHandler=scriptHandler;}
}
