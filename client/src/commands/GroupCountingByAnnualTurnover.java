package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import utility.ScriptChecker;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    /**
     * Проверяет количество аргументов комманды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public Command execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        if (ScriptChecker.isScriptInProcess) System.out.println(getName());
        return this;
    }
}
