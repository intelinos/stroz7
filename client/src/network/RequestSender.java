package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import commands.Command;
import request.Request;
import response.Response;
import utility.LocalDateTimeAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

public class RequestSender {
    OutputStream os;
    public RequestSender(OutputStream os){
        this.os=os;
    }
    public void sendRequest(Command command) {
        try {
            Gson gson= new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting().create();
            Request request = new Request(command.getName(), command.getCommandArgument());
            String gsonString = gson.toJson(request);
            byte[] arr = gsonString.getBytes();
            os.write(arr);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
