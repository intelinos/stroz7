package commands;

import Organization.Organization;
import Organization.OrganizationType;
import exceptions.WrongNumberOfArgumentsException;
import response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

/**
 * Класс команды print_field_descending_type, которая выводит значения поля type всех элементов в порядке убывания.
 */
public class PrintFieldDescendingType extends Command{
    public PrintFieldDescendingType() {
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Выводит значения поля type всех элементов в порядке убывания.";
    }

    @Override
    public String getName() {
        return "print_field_descending_type";
    }


    @Override
    public Response execute(){
        System.out.println("Выполняется команда "+getName());
        if (collectionManager.getCollection().isEmpty()) {
            response = new Response("Коллекция пуста!");
            return response;
        }
        String responseMessage="";
        List<OrganizationType> types = doCommand();
        responseMessage=types.toString();
        response = new Response(responseMessage);
        System.out.println("Команда "+getName()+" была выполнена.");
        return response;
    }

    public List<OrganizationType> doCommand(){
        List<Organization> organizations = new ArrayList<>(collectionManager.getCollection().values());
        return organizations
                .stream()
                .map(org -> org.getType())
                .filter(type -> type!=null)
                .sorted((type1, type2) -> (type2.ordinal() - type1.ordinal()))
                .toList();
    }

}
