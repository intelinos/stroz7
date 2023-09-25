package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import response.Response;
import Organization.Address;

import java.util.*;

/**
 * Класс команды print_unique_postal_address, которая выводит уникальные значения поля postalAddress всех элементов в коллекции.
 */
public class PrintUniquePostalAddress extends Command{

    public PrintUniquePostalAddress() {
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Выводит уникальные значения поля postalAddress всех элементов в коллекции.";
    }

    @Override
    public String getName() {
        return "print_unique_postal_address";
    }


    @Override
    public Response execute(){
        System.out.println("Выполняется команда "+getName());
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            response = new Response("Коллекция пуста!");
            return response;
        }
        List<Address> addresses = doCommand();
        String responseMessage = "Уникальные значения postalAddress всех элементов коллекции: \n";
        for(var addr: addresses){
            responseMessage+=addr.getStreet()+", "+addr.getZipCode()+"\n";
        }
        response = new Response(responseMessage);
        System.out.println("Команда "+getName()+" была выполнена.");
        return response;
    }

    public List<Address> doCommand(){
        List<Organization> organizations = new ArrayList<>(collectionManager.getCollection().values());
        return organizations
                .stream()
                .map(org -> org.getPostalAddress())
                .filter(address -> address!=null)
                .distinct()
                .toList();
    }
}