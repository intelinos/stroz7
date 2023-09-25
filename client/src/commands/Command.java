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

    /**
     * Запускает команду, которая не требует сканер.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если было введено неверное количество аргументов команды.
     */
    @Override
    public Command execute(String[] arguments) throws WrongNumberOfArgumentsException{return null;};
    /**
     * Запускает команду, которая требует сканер.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если было введено неверное количество аргументов команды.
     */
    @Override
    public Command execute(Scanner scanner, String[] arguments) throws WrongNumberOfArgumentsException{return null;};
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
    public CommandArgument getCommandArgument(){
        return commandArgument;
    }
}
