import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import commandProcessing.CommandProcessor;
import commands.Command;
import commands.ExecuteScript;
import connection.TCPNIOConnection;
import exceptions.EmptyCommandNameException;
import exceptions.WrongCommandNameException;
import managers.ServerCommandManager;
import request.Request;
import requestReading.RequestReader;
import response.Response;
import responseSending.ResponseSender;
import utility.LocalDateTimeAdapter;
import utility.ResponseList;
import utility.ScriptChecker;
import utility.ScriptHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class Server {
    TCPNIOConnection connector;
    ResponseSender sender;
    RequestReader reader;
    String args;
    CommandProcessor processor;
    Selector selector;
    public Server(String args){
        this.args=args;
    }
    public void run() {
        try (ServerSocketChannel serv = ServerSocketChannel.open()) {
            processor= new CommandProcessor(args);
            selector = Selector.open();
            InetSocketAddress addr = new InetSocketAddress(InetAddress.getLocalHost(), 43498);
            serv.bind(addr);
            serv.configureBlocking(false);
            serv.register(selector, SelectionKey.OP_ACCEPT);
            processor.loadCollection();
            ServerCommandManager scm = new ServerCommandManager(processor);
            while (true) {
                scm.handle();
                selector.selectNow();
                Set<SelectionKey> keys = selector.selectedKeys();
                for(var iter = keys.iterator(); iter.hasNext(); ) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            doAccept(key);
                        } else if (key.isReadable()) {
                            doRead(key);
                        } else if (key.isWritable()) {
                            doWrite(key);
                        }
                    }
                }
                 /*connector = new TCPNIOConnection();
                SocketChannel sock = connector.connect();
                reader = new RequestReader();
                CommandProcessor processor = new CommandProcessor();
                processor.loadCollection();
                sender = new ResponseSender(sock);
                while (true) {
                    Command command = reader.readRequest(sock);
                    Response response = processor.processCommand(command);

                    //sender.sendResponse();
                }*/
            }
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void doAccept(SelectionKey key) throws IOException {
        var ssc = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = ssc.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public void doRead(SelectionKey key) throws IOException {
        SocketChannel sock = (SocketChannel) key.channel();
        ByteBuffer buf = ByteBuffer.allocate(50000);
        try {
            sock.read(buf);
        } catch (IOException e) {
            System.out.println(e);
            key.cancel();
            sock.close();
        }
        var arr = buf.array();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting().create();
        Charset charset = Charset.defaultCharset();
        String gsonString = new String(arr, charset);
        Request request = gson.fromJson(gsonString.trim(), TypeToken.get(Request.class).getType());
        if (request == null) {
            //key.cancel();
            //sock.close();
            return;
        }
        Response response = processor.processRequest(request);
        sock.register(selector, SelectionKey.OP_WRITE, response);
    }

    public void doWrite(SelectionKey key) throws IOException {
        SocketChannel sock = (SocketChannel) key.channel();
        Response response = (Response) key.attachment();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting().create();
        String gsonStringResponse = gson.toJson(response);
        byte[] arrResponse = gsonStringResponse.getBytes();
        ByteBuffer buf = ByteBuffer.wrap(arrResponse);
        System.out.println("Отправление ответа на клиент... ");
        while (buf.hasRemaining()) {
            sock.write(buf);
        }
        System.out.println("Отправлено!");
        sock.register(selector, SelectionKey.OP_READ);
    }
}
