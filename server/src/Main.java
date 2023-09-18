import Organization.Organization;
import Organization.Coordinates;
import Organization.Address;
import com.google.gson.GsonBuilder;
import request.CommandArgument;
import request.Request;


import java.io.IOException;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
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
        Server server = new Server(args[0]);
        server.run();
    }
}