package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import response.Response;
import utility.LocalDateTimeAdapter;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

public class ResponseReceiver {
    InputStream is;
    String message;

    public ResponseReceiver(InputStream is){
        this.is=is;
    }
    public void readResponse() {
        try {
            Gson gson= new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting().create();
            byte[] arr = new byte[300000];
            //int j=0;
            BufferedInputStream bis = new BufferedInputStream(is);
            bis.read(arr);
            //System.out.println(j);
            Charset charset = Charset.defaultCharset();
            String gsonStringResponse = new String(arr, charset);
            //System.out.println(gsonStringResponse);
            Response response = gson.fromJson(gsonStringResponse.trim(), TypeToken.get(Response.class).getType());
            message = response.getResponseMessage();
            if (response.getResponseArgument()!=null)
                message+="\n"+response.getResponseArgument().toString();
        } catch(IOException e){
            System.out.println("Соединение с сервером было разорвано.");
            System.exit(1);
        }
    }
    public String getMessage(){
        return message;
    }
    public void printMessage(){
        System.out.println(message);
    }
}
