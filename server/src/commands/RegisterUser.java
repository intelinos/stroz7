package commands;

import db.DBConnection;
import response.Response;

import java.sql.SQLException;

public class RegisterUser extends Command{
    public RegisterUser(){
        this.needDB=true;
    }
    @Override
    public String getInfo(){
        return "Регистрация пользователя.";
    }
    @Override
    public String getName(){
        return "register_user";
    }

    @Override
    public Response execute(DBConnection dbConnection) {
        try {
            System.out.println("Регистрация нового пользователя...");
            dbConnection.registerUser(getCommandArgument().getLogin(), getCommandArgument().getPassword());
            response = new Response("");
            System.out.println("Регистрация прошла успешно.");
        } catch (SQLException e) {
            response = new Response(e.getMessage());
        }
        return response;
    }
}
