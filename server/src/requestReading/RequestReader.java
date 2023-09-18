package requestReading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import commands.Command;
import commands.CommandManager;
import commands.Insert;
import managers.CollectionManager;
import managers.FileManager;
import request.Request;
import utility.LocalDateTimeAdapter;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

public class RequestReader {
    public Command readRequest(SocketChannel sock){
        try {
            ByteBuffer buf = ByteBuffer.allocate(50000);
            sock.read(buf);
            var arr = buf.array();
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting().create();
            Charset charset = Charset.defaultCharset();
            String gsonString = new String(arr, charset);
            Request request = gson.fromJson(gsonString.trim(), TypeToken.get(Request.class).getType());
            Command command = convertToCommand(request);
            return command;
        } catch (IOException e){
            System.out.println(e);
        } return null;
    }
    public Command convertToCommand(Request request){
        CommandManager commandManager = new CommandManager();
        Command command = commandManager.getCommands().get(request.getCommandName());
        command.setCommandArgument(request.getCommandArgument());
        System.out.println(command.getName());
        return command;
    }
}
