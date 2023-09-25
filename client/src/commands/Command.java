package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import request.CommandArgument;

import java.util.Scanner;

/**
 * Абстрактная команда.
 */
public abstract class Command implements Executable,Infoable, Argumentable {
    public boolean needScanner;
    private CommandArgument commandArgument;
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
    public Command execute(String login, String password){return this;}
    @Override
    public Command execute(String[] arguments, String login, String password) throws WrongNumberOfArgumentsException{return this;};
    /**
     * Запускает команду, которая требует сканер.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если было введено неверное количество аргументов команды.
     */
    @Override
    public Command execute(Scanner scanner, String[] arguments,String login, String password) throws WrongNumberOfArgumentsException{return this;};
    @Override
    public void setCommandArgument(String login, String password){
        this.commandArgument=new CommandArgument(login, password);
    }
    @Override
    public void setCommandArgument(String key, String login, String password){
        this.commandArgument=new CommandArgument(key, login, password);
    };
    @Override
    public void setCommandArgument(Organization organization,String login, String password){
        this.commandArgument=new CommandArgument(organization, login, password);
    };
    @Override
    public void setCommandArgument(String key, Organization organization, String login, String password){
        this.commandArgument=new CommandArgument(key, organization, login, password);
    };
    @Override
    public CommandArgument getCommandArgument(){
        return commandArgument;
    }
}
