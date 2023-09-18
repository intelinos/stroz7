import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import com.google.gson.GsonBuilder;
import commands.Help;
import request.Request;
import com.google.gson.Gson;
import utility.LocalDateTimeAdapter;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}