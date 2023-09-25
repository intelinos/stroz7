package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import request.CommandArgument;
import response.Response;

import java.util.Scanner;

/**
 * Абстрактная команда.
 */
public abstract class Command implements Executable,Infoable, Argumentable {
    public boolean needScanner;
    private CommandArgument commandArgument;
    protected CollectionManager collectionManager;
    public Response response;
    /**
     * @return Описание команды.
     */
    @Override
    public abstract String getInfo();

    /**
     * @return Имя команды.
     */
    @Override
    public abstract String getName();

    @Override
    public abstract Response execute();
    @Override
    public void setCommandArgument(String key){
        this.commandArgument=new CommandArgument(key);
    };
    @Override
    public void setCommandArgument(Organization organization){
        this.commandArgument=new CommandArgument(organization);
    };
    @Override
    public void setCommandArgument(String key, Organization organization){
        this.commandArgument=new CommandArgument(key, organization);
    };
    @Override
    public void setCommandArgument(CommandArgument commandArgument){
        this.commandArgument = commandArgument;
    }
    @Override
    public CommandArgument getCommandArgument(){
        return commandArgument;
    }
    public void setCollectionManager(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
}
