package commands;

import exceptions.WrongNumberOfArgumentsException;
import response.Response;

import java.util.Map;

/**
 * Класс команды help, которая выводит справку о доступных командах.
 */
public class Help extends Command {
    @Override
    public String getInfo() {
        return "Выводит справку о доступных командах. ";
    }

    @Override
    public String getName() {
        return "help";
    }


    @Override
    public Response execute(){
        System.out.println("Выполняется команда "+getName());
        CommandManager commandManager = new CommandManager();
        String responseMessage="";
        for (Map.Entry<String, Command> pair : commandManager.getCommands().entrySet())
        {
            String name = pair.getKey();
            if (name.equals("save") || name.equals("login_user") || name.equals("register_user"))
                continue;
            Command command = pair.getValue();
            responseMessage += name + ": " + command.getInfo() + "\n";
        }
        System.out.println("Команда "+getName()+" была выполнена.");
        response = new Response(responseMessage);
        return response;
    }
}