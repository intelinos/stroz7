import Organization.Organization;
import Organization.Coordinates;
import Organization.Address;
import com.google.gson.GsonBuilder;
import db.DBConnection;
import db.Database;
import request.CommandArgument;
import request.Request;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import requestReading.RequestReader;
import response.Response;
import responseSending.ResponseSender;
import utility.LocalDateTimeAdapter;


public class Main {
    public static void main(String[] args) {
        try {
            File file = new File(".pgpass");
            Database database = new Database(file);
            DBConnection dbConnection = database.createConnection();
            Server server = new Server(dbConnection);
            server.run();
        } catch(FileNotFoundException e){
            System.out.println("Файл для работы с базой данных не обнаружен.\n"+e);
        } catch(SQLException e){
            System.out.println("Ошибка соединения с базой данных.\n"+e);
        }
    }
}