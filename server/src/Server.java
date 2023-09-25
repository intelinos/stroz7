import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import commandProcessing.CommandProcessor;
import commands.Command;
import commands.ExecuteScript;
import connection.TCPNIOConnection;
import db.DBConnection;
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
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Server {
    ByteBuffer readBuf = ByteBuffer.allocate(50000);
    CommandProcessor processor;
    ForkJoinPool readingPool = new ForkJoinPool();
    ExecutorService writingPool = Executors.newCachedThreadPool();
    Selector selector;
    DBConnection dbConnection;

    public Server(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void run() {
        try (ServerSocketChannel serv = ServerSocketChannel.open()) {
            processor = new CommandProcessor(dbConnection);
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
                            key.cancel();
                        } else if (key.isWritable()) {
                            doWrite(key);
                            key.cancel();
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
        //System.out.println("accepted");
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public void doRead(SelectionKey key) throws IOException {
        RecursiveAction doReadAction = new RecursiveAction() {
            @Override
            protected void compute() {
                SocketChannel sock = (SocketChannel) key.channel();
                readBuf = ByteBuffer.allocate(5000);
                int byteCount = 0;
                try {
                    byteCount = sock.read(readBuf);
                } catch (IOException e) {
                    System.out.println(e);
                    key.cancel();
                    try {
                        sock.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
                if (byteCount == -1) {
                    key.cancel();
                    return;
                }
                requestProcessStart(sock, key);
            }

        };
        readingPool.execute(doReadAction);
    }

    private void requestProcessStart(SocketChannel sock, SelectionKey key) {
        Runnable requestProcessStartTask = new Runnable() {
            @Override
            public void run() {
                byte[] arr = readBuf.array();
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .setPrettyPrinting().setLenient().create();
                Charset charset = Charset.defaultCharset();
                String gsonString = new String(arr, charset);
                Request request = gson.fromJson(gsonString.trim(), TypeToken.get(Request.class).getType());
                if (request == null) {
                    key.cancel();
                    try {
                        sock.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                    return;
                }
                Response response = processor.processRequest(request);
                try {
                    sock.register(selector, SelectionKey.OP_WRITE, response);
                } catch (ClosedChannelException e) {
                    System.out.println("Канал закрыт!\n" + e);
                }
            }

        };
        Thread requestProcessStartThread = new Thread(requestProcessStartTask);
        requestProcessStartThread.start();

    }

    public void doWrite(SelectionKey key) throws IOException {
        Runnable doWriteTask = () -> {
            SocketChannel sock = (SocketChannel) key.channel();
            Response response = (Response) key.attachment();
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting().create();
            String gsonStringResponse = gson.toJson(response);
            // System.out.println(gsonStringResponse);
            byte[] arrResponse = gsonStringResponse.getBytes();
            // System.out.println(arrResponse.length);
            ByteBuffer buf = ByteBuffer.wrap(arrResponse);
            try {
                // int i=0;
                while (buf.hasRemaining()) {
                    sock.write(buf);
                    //   i++;
                }
                System.out.println("Ответ отправлен. ");
                sock.register(selector, SelectionKey.OP_READ);
            } catch (IOException e){
                System.out.println(e);
            }
        };
        writingPool.execute(doWriteTask);
    }
}
