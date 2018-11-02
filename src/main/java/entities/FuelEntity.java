package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(видов топлива) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "ТОПЛИВО", schema = "s242419", catalog = "studs")
public class FuelEntity {
    /** Юзлесс идентификатор */
    private FuelEntityKey id;

    public FuelEntity() { }

    public FuelEntity(String naming) {
        this.id = new FuelEntityKey(naming);
    }

    /**
     * @return Юзлесс идентификатор
     */
    @Id
    public FuelEntityKey getId() {
        return id;
    }

    /**
     * Юзлесс функция для определения юзлесс идентификатора
     */
    public void setId(FuelEntityKey id) {
        this.id = id;
    }
}
