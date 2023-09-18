package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import response.Response;
import utility.LocalDateTimeAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

public class ResponseReceiver {
    InputStream is;
    public ResponseReceiver(InputStream is){
        this.is=is;
    }
    public void readResponse() {
        try {
            Gson gson= new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting().create();
            byte[] arr = new byte[5000];
            is.read(arr);
            Charset charset = Charset.defaultCharset();
            String gsonStringResponse = new String(arr, charset);
            //System.out.println(gsonStringResponse.trim());
            Response response = gson.fromJson(gsonStringResponse.trim(), TypeToken.get(Response.class).getType());
            String message = response.getResponseMessage();
            System.out.println(message);
            if (response.getResponseArgument()!=null)
                System.out.println(response.getResponseArgument().toString());
        } catch(IOException e){
            System.out.println("Соединение с сервером было разорвано.");
            System.exit(1);
        }
    }
}
