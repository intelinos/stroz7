package validators;

import Organization.Organization;
import exceptions.WrongDeserializationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Используется для валидации дубликатных id.
 */
public class IdDuplicateValidator implements ValidatorWithException<Map<Integer, Organization>>{
    /**
     * Валидирует дубликатные id.
     * @param collection Хранимая коллекция.
     * @return true - если в коллекции нет неуникальных id.
     * @throws WrongDeserializationError Если были обнаружены неуникальные id.
     */
    @Override
    public boolean validate(Map<Integer, Organization> collection) throws WrongDeserializationError {
        List<Integer> idDuplicateList = new ArrayList<>();
        for(Map.Entry<Integer, Organization> pair : collection.entrySet()) {
            if (!Organization.addIdToSet(pair.getValue().getId()))
                idDuplicateList.add(pair.getValue().getId());
        }
        if (!idDuplicateList.isEmpty()) {
            String message = "";
            for(Integer duplicatedId : idDuplicateList) {
                message += duplicatedId + " ";
            }
            throw new WrongDeserializationError("При загрузке были обнаружены неуникальные id: " + message);
        }
        return true;
    }
}
