package Organization;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс, объекты которого хранятся в коллекции.
 */
public class Organization implements Comparable<Organization> {
    /**
     * Хранит значение id, которое будет присвоено следующему объекту при его создании.
     */
    private static Integer nextId=1;
    /**
     * Хранит уникальные значения id. Необходим для поддержания уникальности id различных объектов, хранимых в коллекции.
     */
    private static Set<Integer> idSet = new HashSet<>();
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float annualTurnover; //Значение поля должно быть больше 0
    private long employeesCount; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле может быть null

    public Organization(String name,Coordinates coordinates,
                        float annualTurnover, long employeesCount,
                        OrganizationType type, Address postalAddress) {
        while(!idSet.add(nextId)) {
            nextId++;
        }
        this.id = nextId;
        nextId++;
        this.name = name;
        this.coordinates = coordinates;
        creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    /**
     * @return Поле id объекта.
     */
    public Integer getId() {return id;}

    /**
     * Устанавливает id объекта.
     * @param id id, который будет установлен.
     */
    public void setId(Integer id) {this.id = id;}

    /**
     * Добавляет в idSet введенный id, если он еще не существует.
     * @param id id, который будет добавлен.
     * @return true - если id был добавлен в idSet (такого значения id не было), false - если id не был добавлен в idSet (такое значение id уже есть).
     */
    public static boolean addIdToSet(Integer id) {return idSet.add(id);}

    /**
     * @return Имя объекта.
     */
    public String getName() {return name;}

    /**
     * @return Годовой оборот объекта.
     */
    public float getAnnualTurnover() {return annualTurnover;}

    /**
     * @return Число сотрудников объекта.
     */
    public long getEmployeesCount() {return employeesCount;}
    /**
     * @return Дату создания объекта.
     */
    public LocalDateTime getCreationDate() {return creationDate;}

    /**
     * Устанавливает дату создания объекта.
     * @param creationDate дата создания объекта.
     */
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}
    /**
     * @return Адрес объекта.
     */
    public Address getPostalAddress() {return postalAddress;}
    /**
     * @return Тип организации объекта.
     */
    public OrganizationType getType() {return type;}
    /**
     * @return Координаты объекта.
     */
    public Coordinates getCoordinates() {return coordinates;}

    @Override
    public int compareTo(Organization organization) {
        int result = Float.compare(annualTurnover, organization.getAnnualTurnover());
        if (result==0) result = Long.compare(employeesCount, organization.getEmployeesCount());
        return result;
    }

    @Override
    public String toString(){
        String result;
        result=" Organization: " +"\n"+
                "id = " + id +",\n"+
                "name = " + name +",\n"+
                "coordinates = " + coordinates.toString() +",\n"+
                "creationDate = " + creationDate.toString() +",\n"+
                "annualTurnover = " + annualTurnover +",\n"+
                "employeesCount = " + employeesCount +",\n";
        if (type == null) result += "type = null" + ",\n";
        else result += "type = " + type + ",\n";

        if (postalAddress == null) result += "postalAddress = null" + "\n";
        else result += "postalAddress = " + postalAddress.toString()+"\n";
        return result;
    }
}


