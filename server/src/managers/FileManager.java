package managers;

import Organization.Organization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import utility.LocalDateTimeAdapter;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Управляет чтением/записью в файл.
 */
public class FileManager {
    private Gson gson= new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                                        .setPrettyPrinting().create();
    public static File file;
    public FileManager(String arg) {
        file = new File(arg);
    }

    /**
     * Записывает коллекцию в файл в формате JSON.
     * @param collection Записываемая коллекция.
     * @throws FileNotFoundException Если файл для записи не был найден, или отсутствуют права на запись в файл.
     * @throws IOException Если возникли другие ошибки при записи.
     */
    public void writeCollectionToFile (Map<Integer, Organization> collection) throws FileNotFoundException, IOException {
        try(FileWriter collectionFileWriter = new FileWriter(file)) {
            collectionFileWriter.write(gson.toJson(collection));
        }
    }

    /**
     * Читает коллекцию из файла.
     * @throws FileNotFoundException Если файл для чтения не был найден, или отсутствуют права на чтение файла.
     * @throws JsonSyntaxException Если при чтении файла был обнаружен искаженный синтаксис.
     * @throws DateTimeParseException Если при чтении файла поле creationDate содержит данные, не соответствующие формату YYYY-MM-DDTHH-MM-SS
     * @throws IOException Если возникли другие ошибки ввода-вывода.
     */
    public Map<Integer, Organization> readCollectionFromFile() throws FileNotFoundException,  JsonSyntaxException, DateTimeParseException, IOException {
        try (BufferedInputStream collectionBIS = new BufferedInputStream(new FileInputStream(file))) {
            byte[] b = collectionBIS.readAllBytes();
            String jsonString = new String(b);
            Map<Integer, Organization> collection = gson.fromJson(jsonString, new TypeToken<TreeMap<Integer, Organization>>() {
            }.getType());
            if (collection == null) return new TreeMap<>();
            return collection;
        }
    }
}

