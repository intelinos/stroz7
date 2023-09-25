import commands.Command;
import commands.CommandManager;
import network.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    TCPConnector connector;
    CommandManager commandManager;
    RequestSender sender;
    ResponseReceiver receiver;
    Authenticator authenticator;
    Scanner scanner;
    public void run(){
        connector = new TCPConnector();
        connector.connect();
        scanner = new Scanner(System.in);
        sender = new RequestSender(connector.getOutputStream());
        receiver = new ResponseReceiver(connector.getInputStream());
        authenticator = new Authenticator(scanner, sender, receiver);
        authenticator.authenticateUser();
        commandManager = new CommandManager(sender, receiver, authenticator.getLogin(), authenticator.getPassword());
        while (true) {
            try {
                Command command = commandManager.invokeCommand(scanner);
                if (command==null) {
                    continue;
                }
                sender.sendRequest(command);
                receiver.readResponse();
                receiver.printMessage();
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод.");
                scanner = new Scanner(System.in);
            }
        }
    }
}
