import commands.Command;
import commands.CommandManager;
import network.RequestSender;
import network.ResponseReceiver;
import network.TCPConnector;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    TCPConnector connector;
    CommandManager commandManager;
    RequestSender sender;
    ResponseReceiver receiver;
    Scanner scanner;
    public void run(){
        connector = new TCPConnector();
        connector.connect();
        scanner = new Scanner(System.in);
        sender = new RequestSender(connector.getOutputStream());
        receiver = new ResponseReceiver(connector.getInputStream());
        commandManager = new CommandManager(sender, receiver);
        while (true) {
            try {
                Command command = commandManager.invokeCommand(scanner);
                if (command==null) {
                    continue;
                }
                sender.sendRequest(command);
                receiver.readResponse();
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод.");
                scanner = new Scanner(System.in);
            }
        }
    }
}
