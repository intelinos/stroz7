package commandProcessing;

import Organization.Organization;
import Organization.Coordinates;
import Organization.Address;
import com.sun.tools.javac.Main;
import commands.Command;
import commands.CommandManager;
import exceptions.WrongDeserializationError;
import managers.CollectionManager;
import managers.FileManager;
import request.Request;
import response.Response;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CommandProcessor {
    Response response;
    String args;
    FileManager fileManager;
    CollectionManager collectionManager;
    public CommandProcessor(String args){
        this.args = args;
    }
    public void loadCollection(){
        try {
            fileManager= new FileManager(args);
            collectionManager = new CollectionManager(fileManager);
            collectionManager.loadCollectionFromFile();
        } catch(FileNotFoundException e){
            System.out.println(e);
            System.exit(1);
        } catch(WrongDeserializationError e){
            System.out.println(e);
            System.exit(1);
        } catch(IOException e){
            System.out.println(e);
            System.exit(1);
        }
    }
    public Response processRequest(Request request) {
            Command command = convertToCommand(request);
            command.setCollectionManager(collectionManager);
            response = command.execute();
            return response;
    }

    public Command convertToCommand(Request request){
        CommandManager commandManager = new CommandManager();
        Command command = commandManager.getCommands().get(request.getCommandName());
        command.setCommandArgument(request.getCommandArgument());
        //System.out.println(command.getName());
        return command;
    }
}
