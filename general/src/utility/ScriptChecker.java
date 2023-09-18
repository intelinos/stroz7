package utility;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Позволяет контролировать рекурсию, а также правильную обработку ошибок во время работы скриптов.
 */
public class ScriptChecker {
    /**
     * True - если выполняется скрипт, false - если никакой скрипт не выполняется в данный момент.
     */
    public static boolean isScriptInProcess;
    /**
     * Необходим для обнаружения рекурсий. Содержит уникальные файлы запускаемых скриптов.
     */
    private static Set<File> scriptSet = new HashSet<>();

    /**
     * Добавляет файл скрипта в scriptSet. Если введенный скрипт уже присутствует в scriptSet, то означает, что началась рекурсия.
     * @param file Файл скрипта.
     * @return true - если файл скрипта был добавлен в sciptSet (такого файла еще не было), false - если не был добавлен (уже был такой файл).
     */
    public static boolean addInScriptSet(File file) {
        return scriptSet.add(file);
    }

    /**
     * Очищает scriptSet. Позволяет корректно выходить из скриптов.
     */
    public static void clearScriptSet() {
        scriptSet.clear();
    }

    public static void removeFromScriptSet(File file){
        scriptSet.remove(file);
    }
    /**
     * @return true - если scriptSet пустой, fasle - если не пустой.
     */
    public static boolean isScriptSetEmpty() {
        return scriptSet.isEmpty();
    }
}
