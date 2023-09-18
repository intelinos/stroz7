package responseSending;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import response.Response;
import utility.LocalDateTimeAdapter;
import utility.ResponseList;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;

public class ResponseSender {
    SocketChannel sock;
    public ResponseSender(SocketChannel sock){
        this.sock=sock;
    }
    public void sendResponse(ResponseList responseList){
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting().create();
            String gsonStringResponse = gson.toJson(responseList);
            byte[] arrResponse = gsonStringResponse.getBytes();
            ByteBuffer buf = ByteBuffer.wrap(arrResponse);
            sock.write(buf);
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
