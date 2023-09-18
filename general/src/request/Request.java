package request;

import java.io.Serializable;

public class Request implements Serializable {
    //private static final long serialVersionUID = 35434532452L;
    private String commandName;
    private CommandArgument commandArgument;
    public Request(String commandName, CommandArgument commandArgument) {
        this.commandName = commandName;
        this.commandArgument = commandArgument;
    }
    public String getCommandName() {
        return commandName;
    }
    public CommandArgument getCommandArgument() {
        return commandArgument;
    }
}
