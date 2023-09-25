package network;

import commands.Command;
import commands.LoginUser;
import commands.RegisterUser;
import exceptions.AuthenticationException;
import response.Response;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Authenticator {
    private Scanner scanner;
    private String login;
    private String password;
    private RequestSender sender;
    private ResponseReceiver receiver;
    public Authenticator(Scanner scanner, RequestSender sender, ResponseReceiver receiver){
        this.scanner = scanner;
        this.sender = sender;
        this.receiver = receiver;
    }
    public void authenticateUser(){
        while (true) {
            System.out.println("Для регистрации введите \"reg\", для входа введите \"log\".");
            try {
                String word = scanner.nextLine().trim();
                if (word.equals("reg")) {
                    readLoginAndPassword();
                    System.out.println("read end");
                    registerUser();
                    return;
                } else if (word.equals("log")) {
                    readLoginAndPassword();
                    loginUser();
                    return;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод.");
                scanner = new Scanner(System.in);
            } catch (AuthenticationException e){
                System.out.println(e);
            }
        }
    }

    private void readLoginAndPassword(){
        while(true) {
            try {
                System.out.println("Введите логин: ");
                login = scanner.nextLine().trim();
                if (login.isBlank() || login.isEmpty()) {
                    System.out.println("Логин не может быть пустым!");
                    continue;
                }
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Неверный ввод!");
                scanner = new Scanner(System.in);
            }
        }
        while(true) {
            try{
                System.out.println("Введите пароль: ");
                password = (new String(System.console().readPassword())).trim();
                if(password.isEmpty() || password.isBlank()) {
                    System.out.println("Пароль не может быть пустым!");
                    continue;
                }
                break;
            } catch(NullPointerException e){
                System.out.println("Неверный ввод!");
            }
        }
    }

    private void loginUser() throws AuthenticationException {
        Command command = new LoginUser().execute(login, password);;
        sender.sendRequest(command);
        receiver.readResponse();
        if(receiver.getMessage().equals("")){
            System.out.println("Вы успешно залогинились.");
        } else throw new AuthenticationException(receiver.getMessage());
    }

    private void registerUser() throws AuthenticationException {
        Command command = new RegisterUser().execute(login, password);
       // System.out.println("command got");
        sender.sendRequest(command);
        //System.out.println("sended");
        receiver.readResponse();
        //System.out.println("received");;
        if(receiver.getMessage().equals("")){
            System.out.println("Вы успешно зарегистрировались.");
        } else throw new AuthenticationException(receiver.getMessage());
    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
}
