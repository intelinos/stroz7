package commands;

public class LoginUser extends Command{
    public LoginUser(){
    }
    @Override
    public Command execute(String login, String password){
        setCommandArgument(login, password);
        return this;
    }
    @Override
    public String getInfo() {
        return "Логинит пользователя.";
    }

    @Override
    public String getName() {
        return "login_user";
    }
}
