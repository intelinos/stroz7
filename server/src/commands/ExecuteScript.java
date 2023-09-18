package commands;

import exceptions.ScriptRecursionException;
import exceptions.WrongNumberOfArgumentsException;
import response.Response;
import utility.ResponseList;
import utility.ScriptChecker;
import utility.ScriptHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Класс команды execute_script, которая считывает и исполняет скрипт из указанного файла.
 */
public class ExecuteScript extends Command{
    private ScriptHandler scriptHandler;
    private File file;
    public ExecuteScript() {
        this.needScanner=false;
    }

    @Override
    public String getInfo() {
        return "Считывает и исполняет скрипт из указанного файла.";
    }

    @Override
    public String getName() {
        return "execute_script";
    }


    @Override
    public Response execute(){
        String fileName = getCommandArgument().getKeyArgument();
        try {
            file = new File(fileName);
            if (!file.exists()) throw new FileNotFoundException();
            if (file.length()==0) {
                response = new Response("Файл "+fileName+" пуст.");
                return response;
            }
            if(!ScriptChecker.addInScriptSet(file)) throw new ScriptRecursionException();
            scriptHandler.setCollectionManager(collectionManager);
            //ResponseList.responseList.add(new Response("Начало выполнения скрипта "+fileName));
            scriptHandler.processScript(this);
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
            ScriptChecker.isScriptInProcess=false;
            ScriptChecker.clearScriptSet();
        } catch (FileNotFoundException e) {
            response = new Response("Файл с именем" +fileName+" не существует.");
            ScriptChecker.clearScriptSet();
        } catch (ScriptRecursionException e) {
            response = new Response("Обнаружена рекурсия! Скрипт - "+fileName);
            ScriptChecker.clearScriptSet();
        } return response;
    }

    public void setScriptHandler(ScriptHandler scriptHandler){
        this.scriptHandler=scriptHandler;
    }
    public File getFile(){
        return file;
    }
}
