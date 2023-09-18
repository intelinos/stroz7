package commands;

/**
 * Интерфейс для всего, позволяет выдать имя и описание.
 */
public interface Infoable {
    /**
     * @return Имя.
     */
    String getName();

    /**
     * @return Описание.
     */
    String getInfo();
}
