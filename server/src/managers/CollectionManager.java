package managers;

import Organization.Organization;
import com.google.gson.JsonSyntaxException;
import db.DBConnection;
import exceptions.WrongDeserializationError;
import validators.IdDuplicateValidator;
import validators.OrganizationValidator;
import validators.ValidatorWithException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Управляет коллекцией.
 */
public class CollectionManager {
    private int IdCounter=1;
    private Set<Integer> IdSet=new HashSet<>();
    private FileManager fileManager;
    public CollectionManager(DBConnection dbConnection) {
        this.dbConnection=dbConnection;
    }
    private Map<Integer, Organization> collection;
    private LocalDateTime lastTimeOfSaving;
    private LocalDateTime initiationDate;
    private DBConnection dbConnection;
    /**
     * @return Время последнего сохранения коллекции.
     */
    public LocalDateTime getLastTimeOfSaving() { return lastTimeOfSaving; }

    /**
     * @return Время инициализации коллекции.
     */
    public LocalDateTime getInitiationDate() { return initiationDate;}

    /**
     * Устанавливает время инициализации коллекции.
     * @param initiationDate Время инициализации коллекции.
     */
    public void setInitiationDate(LocalDateTime initiationDate) {
        this.initiationDate = initiationDate.truncatedTo(ChronoUnit.SECONDS);
    }

    /**
     * Добавляет элемент Organization с определенным ключом в коллекцию.
     * @param key Ключ элемента в коллекции.
     * @param organization Элемент Organization для добавления в коллекцию.
     */
    public void addToTheCollection(int key, Organization organization) {
        //setId(organization);
//        organization.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        collection.put(key ,organization);
    }

    public void setId(Organization organization){
        while(!IdSet.add(IdCounter))
            IdCounter++;
        organization.setId(IdCounter);
    }

    /**
     * Удаляет элемент с определенным ключом из коллекции.
     * @param key Ключ элемента коллекции.
     */
    public void deleteFromTheCollection(int key) {
        collection.remove(key);
    }

    /**
     * Сохраняет коллекцию в файл при помощи managers.FileManager
     * @see FileManager
     * @throws FileNotFoundException Если файл для записи не был найден, или отсутствуют права на запись в файл.
     * @throws IOException Если возникли другие ошибки при записи.
     */
    public void saveCollectionToFile() throws FileNotFoundException, IOException {
        fileManager.writeCollectionToFile(collection);
        lastTimeOfSaving = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    /**
     * Загружает коллекцию из файла при помощи managers.FileManager, проверяет поля на валидность.
     * @see FileManager
     * @throws FileNotFoundException Если файл для чтения не был найден, или отсутствуют права на чтение файла.
     * @throws JsonSyntaxException Если при чтении файла был обнаружен искаженный синтаксис.
     * @throws WrongDeserializationError Если у прочитанной коллекции были обнаружены невалидные поля.
     * @throws DateTimeParseException Если при чтении файла поле creationDate содержит данные, не соответствующие формату YYYY-MM-DDTHH-MM-SS
     * @throws IOException Если возникли другие ошибки ввода-вывода.
     */
    public void loadCollectionFromDB() throws FileNotFoundException,  JsonSyntaxException, WrongDeserializationError, DateTimeParseException, SQLException,IOException {
        this.collection = dbConnection.getCollectionFromDB();
        initiationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        /*ValidatorWithException<Organization> organizationValidator= new OrganizationValidator();
        ValidatorWithException<Map<Integer, Organization>> idDuplicateValidator = new IdDuplicateValidator();
        Map<Integer, Organization> collection = fileManager.readCollectionFromFile();
        initiationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        int key=0;
        try {
            for(Map.Entry<Integer, Organization> pair : collection.entrySet()) {
                key=pair.getKey();
                Organization organization = pair.getValue();
                IdSet.add(organization.getId());
                organizationValidator.validate(organization);
            }
        } catch (WrongDeserializationError e) {
            throw new WrongDeserializationError("Ошибка при загрузке элемента с ключом " + key+". "+e.getMessage());
        }
        try {
            idDuplicateValidator.validate(collection);
            this.collection = collection;
        } catch (WrongDeserializationError e) {
            throw new WrongDeserializationError(e.getMessage());
        }*/

    }

    /**
     * @return Текущую коллекцию.
     */
    public  Map<Integer, Organization> getCollection() {return collection;}

    public void changeCollection(Map<Integer, Organization> collection){
        this.collection = collection;
    }
}
