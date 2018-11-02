package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(конвертера лимитов) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "КОНВЕРТЕР", schema = "s242419", catalog = "studs")
public class ConverterEntity {
    /** Юзлесс идентификатор */
    private ConverterEntityKey id;

    /**
     * @return Юзлесс идентификатор
     */
    @Id
    public ConverterEntityKey getId() {
        return id;
    }

    /**
     * Юзлесс функция для определения юзлесс идентификатора
     */
    public void setId(ConverterEntityKey id) {
        this.id = id;
    }
}
