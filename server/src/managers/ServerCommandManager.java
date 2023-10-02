package managers;

import com.google.gson.stream.JsonToken;
import commandProcessing.CommandProcessor;
import commands.Command;
import commands.Exit;
import exceptions.EmptyCommandNameException;
import exceptions.WrongCommandNameException;
import request.Request;
import utility.ScriptChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerCommandManager {
    private CommandProcessor processor;
    public ServerCommandManager(CommandProcessor processor){
        this.processor=processor;
    }
    public void handle() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Scanner scanner = new Scanner(reader);
            if (!reader.ready()) {
                return;
            }
            String commandName = (scanner.nextLine().trim());
            if (commandName.isEmpty() || commandName.isBlank()) throw new EmptyCommandNameException();
            if (!commandName.equals("exit")) throw new WrongCommandNameException();
            new Exit().execute();
        } catch (EmptyCommandNameException e) {
            System.out.println("Имя команды не может быть пустым. ");
        } catch (WrongCommandNameException e) {
            System.out.println("Ошибка, команды с таким именем не существует.");
        } catch (NoSuchElementException e) {
            System.out.println("Неверный ввод.");
            //scanner = new Scanner(System.in);
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
