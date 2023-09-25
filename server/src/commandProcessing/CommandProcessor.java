package commandProcessing;

import commands.Command;
import commands.CommandManager;
import db.DBConnection;
import exceptions.WrongDeserializationError;
import managers.CollectionManager;
import managers.FileManager;
import request.Request;
import response.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class CommandProcessor {
    Response response;
    FileManager fileManager;
    CollectionManager collectionManager;
    DBConnection dbConnection;
    public CommandProcessor( DBConnection dbConnection){
        this.dbConnection=dbConnection;
    }
    public void loadCollection(){
        try {
            // fileManager= new FileManager();
            collectionManager = new CollectionManager(dbConnection);
            collectionManager.loadCollectionFromDB();
        } catch(FileNotFoundException e){
            System.out.println(e);
            System.exit(1);
        } catch(WrongDeserializationError e){
            System.out.println(e);
            System.exit(1);
        } catch(SQLException e){
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
        try{
            dbConnection.loginUser(command.getCommandArgument().getLogin(), command.getCommandArgument().getPassword());
        } catch (SQLException e){
            System.out.println(e);
        }
        if(command.needDB)
            response=command.execute(dbConnection);
        else
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