package commands;

import exceptions.ScriptRecursionException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import network.RequestSender;
import network.ResponseReceiver;
import response.Response;
import utility.ScriptChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Класс команды execute_script, которая считывает и исполняет скрипт из указанного файла.
 */
public class ExecuteScript extends Command{
    private RequestSender sender;
    private ResponseReceiver receiver;
    private CommandManager commandManager;
    String fileName;
    public ExecuteScript(CommandManager commandManager, RequestSender sender, ResponseReceiver receiver) {
        this.needScanner=false;
        this.commandManager=commandManager;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public String getInfo() {
        return "Считывает и исполняет скрипт из указанного файла.";
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    /**
     * Проверяет количество аргументов команды execute_script и выполняет ее. Выполнение прерывается если не был найден файл скрипта/ нет прав на чтение этого файла, а также если была обнаружена рекурсия.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если не было введено аргументов команды, либо их количество больше одного.
     */
    @Override
    public Command execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length != 2) throw new WrongNumberOfArgumentsException();
        try {
            fileName = arguments[1];
            setCommandArgument(fileName);
            File file = new File(fileName);
            if (!file.exists()) throw new FileNotFoundException();
            if (file.length() == 0) {
                System.out.println("Файл " + fileName + " пуст!");
                return null;
            }
            if (!ScriptChecker.addInScriptSet(file)) throw new ScriptRecursionException();
            Scanner scanner = new Scanner(file);
            System.out.println("Начинаем выполнять скрипт "+fileName);
            while (scanner.hasNextLine()) {
                ScriptChecker.isScriptInProcess = true;
                Command command = commandManager.invokeCommand(scanner);
                if(command!=null) {
                    sender.sendRequest(command);
                    receiver.readResponse();
                }
                if (ScriptChecker.isScriptSetEmpty()) {
                    ScriptChecker.isScriptInProcess = false;
                    return null;
                }
            }
            //ResponseList.responseList.add(new Response("Начало выполнения скрипта "+fileName));
            /*Scanner scanner = new Scanner(file);
            CommandManager commandManager = new CommandManager();
            commandManager.setCollectionManager(collectionManager);
            while (scanner.hasNextLine()) {
                ScriptChecker.isScriptInProcess=true;
                response = commandManager.invokeCommand(scanner);
                if(ScriptChecker.isScriptSetEmpty()) {
                    ScriptChecker.isScriptInProcess=false;
                    return response;
                }
            }*/
            //ResponseList.responseList.add(new Response("Скрипт "+fileName+" успешно выполнен."));
            ScriptChecker.isScriptInProcess = false;
            ScriptChecker.removeFromScriptSet(file);
            return null;
        }   catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
            ScriptChecker.clearScriptSet();
        } catch (ScriptRecursionException e) {
            System.out.println("Обнаружена рекурсия! Скрипт - " + fileName);
            ScriptChecker.clearScriptSet();
        } return null;
    }
}
