package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import response.Response;
import utility.LocalDateTimeAdapter;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ResponseReceiver {
    private static final int SIZE = 300000;
    InputStream is;
    String message;
    byte[] arr= new byte[SIZE];;

    public ResponseReceiver(InputStream is){
        this.is=is;
    }
    public void readResponse() {
        try {
            Gson gson= new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting().create();
            //int j=0;
            BufferedInputStream bis = new BufferedInputStream(is);
            //arr = new byte[SIZE];
            /*bis.read(arr,0,4);
            int size = ByteBuffer.wrap(arr).getInt();
            arr=new byte[size];*/
            int size = bis.read(arr);
            //ystem.out.println("\n"+arr.length);
            //System.out.println(j);
            Charset charset = Charset.defaultCharset();
            String gsonStringResponse = new String(arr,charset);
            Arrays.fill(arr, 0, size+1, (byte)0);
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
    public String getMessage() {
        return message;
    }
    public void printMessage(){
        System.out.println(message);
    }
}
