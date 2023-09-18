package utility;

import commandProcessing.CommandProcessor;
import commands.Command;
import commands.CommandManager;
import commands.ExecuteScript;
import managers.CollectionManager;
import requestReading.RequestReader;
import response.Response;
import responseSending.ResponseSender;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class ScriptHandler {
    private ResponseSender sender;
    private CollectionManager collectionManager;
    private Response response;
    public void processScript(ExecuteScript script){
        try {
            Scanner scanner = new Scanner(script.getFile());
            CommandManager commandManager = new CommandManager();
            commandManager.setCollectionManager(collectionManager);
            commandManager.setScriptHandler(this);
            while (scanner.hasNextLine()) {
                ScriptChecker.isScriptInProcess = true;
                response = commandManager.invokeCommand(scanner);
              //  ResponseList.responseList.add(response);
                if (ScriptChecker.isScriptSetEmpty()) {
                    ScriptChecker.isScriptInProcess = false;
                }
            }
        } catch (FileNotFoundException e){
        }
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}
