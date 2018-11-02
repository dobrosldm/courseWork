package entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FuelEntityKey implements Serializable {
    /** Вид топлива */
    private String naming;

    /** Создает новый вид топлива без заданного названия */
    public FuelEntityKey() {
    }

    /** Создает новый вид топлива с заданным названием */
    public FuelEntityKey(String naming) {
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

        FuelEntityKey that = (FuelEntityKey) o;

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
}
