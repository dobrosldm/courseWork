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
    /** Вид топлива */
    private String naming;

    /** Создает новый вид топлива без заданного названия
     *  @see FuelEntity#FuelEntity(String)
     */
    public FuelEntity() {
    }

    /** Создает новый вид топлива с заданным названием
     *  @see FuelEntity#FuelEntity()
     */
    public FuelEntity(String naming) {
        this.naming = naming;
    }

    /**
     * @return Вид топлива
     */
    @Basic
    @Column(name = "НАЗВАНИЕ", nullable = true, length = -1)
    public String getNaming() {
        return naming;
    }

    /**
     * Функция для определения вида топлива
     */
    public void setNaming(String naming) {
        this.naming = naming;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuelEntity that = (FuelEntity) o;

        if (naming != null ? !naming.equals(that.naming) : that.naming != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта FuelEntity
     */
    @Override
    public int hashCode() {
        return naming != null ? naming.hashCode() : 0;
    }

    /** Юзлесс идентификатор */
    private Integer id;

    /**
     * @return Юзлесс идентификатор
     */
    @Id
    public Integer getId() {
        return id;
    }

    /**
     * Юзлесс функция для определения юзлесс идентификатора
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
