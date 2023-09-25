package commands;

public class RegisterUser extends Command{
    @Override
    public Command execute(String login, String password){
        setCommandArgument(login, password);
        return this;
    }
    @Override
    public String getInfo(){
        return "Регистрация пользователя.";
    }
    @Override
    public String getName(){
        return "register_user";
    }
}
