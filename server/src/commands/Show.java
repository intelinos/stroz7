package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import response.Response;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Класс команды show, которая выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 */
public class Show extends Command{
    @Override
    public String getInfo() {
        return "Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.";
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public Response execute(){
        System.out.println("Выполняется команда "+getName());
        Response response;
        Map<Integer, Organization> collection = collectionManager.getCollection();
        System.out.println("Команда "+getName()+" была выполнена.");
       // System.out.println(collection);
        /*for(Organization organization: collection.values()){
            if(organization.getPostalAddress()!=null){
                if(organization.getPostalAddress().getStreet()==null){
                    System.out.println("WHATTTTTTTTTTTTTTTTTT?????????");
                }
            }
        }*/
        if (collection.isEmpty()) {
            response = new Response("Коллекция пуста!");
        }else response = new Response("Текущая коллекция: ", collection);
     //   else{
      //      Map<Integer, Organization> collection1 = new TreeMap<>((i1, i2) -> collection.get(i2).getCoordinates().compareTo(collection.get(i1).getCoordinates()));
        //     collection1.putAll(collection);
        //    response = new Response("Текущая коллекция: ", collection1);
        //}
        return response;
    }
}
