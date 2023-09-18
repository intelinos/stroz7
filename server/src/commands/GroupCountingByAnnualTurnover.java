package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import response.Response;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс команды group_counting_by_annual_turnover, которая сгруппировывает элементы коллекции по значению поля annualTurnover, выводит количество элементов в каждой группе.
 */
public class GroupCountingByAnnualTurnover extends Command{
    public GroupCountingByAnnualTurnover() {
        this.needScanner = false;
    }

    @Override
    public String getInfo() {
        return "Сгруппировывает элементы коллекции по значению поля annualTurnover, выводит количество элементов в каждой группе.";
    }

    @Override
    public String getName() {
        return "group_counting_by_annual_turnover";
    }


    @Override
    public Response execute(){
        System.out.println("Выполняется команда "+getName());
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            response = new Response("Коллекция пуста!");
            return response;
        }
        Map<Float, Long> annualTurnovers=doCommand();
        System.out.println("Команда "+getName()+" была выполнена.");
        response = new Response(annualTurnovers.toString());
        return response;
    }
    public Map<Float, Long> doCommand(){
        List<Organization> organizations = new ArrayList<>(collectionManager.getCollection().values());
        return organizations
                .stream()
                .map(org -> org.getAnnualTurnover())
                .collect(Collectors.groupingBy(at -> at, Collectors.counting()));
    }
}
