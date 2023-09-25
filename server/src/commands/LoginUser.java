package commands;

import db.DBConnection;
import response.Response;

import java.sql.SQLException;

public class LoginUser extends Command{
    public LoginUser(){
        this.needDB=true;
    }

    @Override
    public String getInfo() {
        return "Логинит пользователя.";
    }

    @Override
    public String getName() {
        return "login_user";
    }

    @Override
    public Response execute(DBConnection dbConnection) {
        try {
            System.out.println("Вход нового пользователя...");
            dbConnection.loginUser(getCommandArgument().getLogin(), getCommandArgument().getPassword());
            response = new Response("");
            System.out.println("Новый пользователь успешно залогинился.");
        } catch (SQLException e) {
            response = new Response(e.getMessage());
        }
        return response;
    }
}
