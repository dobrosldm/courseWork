package entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Класс, представляющий множественный первичный ключ для объекта базы данных(акции) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
public class StockEntityPK implements Serializable {
    /** Название акции */
    private String naming;
    /** День проведения акции */
    private short stockDay;
    /** Месяц проведения акции */
    private short stockMonth;

    /** Создает связку ключей для новой акции с заданными параметрами */
    public StockEntityPK(String naming, short stockDay, short stockMonth) {
        this.naming = naming;
        this.stockDay = stockDay;
        this.stockMonth = stockMonth;
    }

    public StockEntityPK() {
    }

    /**
     * @return Название акции
     */
    @Column(name = "название", nullable = false, length = -1)
    @Id
    public String getNaming() {
        return naming;
    }

    /**
     * Функция для определения названия акции
     */
    public void setNaming(String naming) {
        this.naming = naming;
    }

    /**
     * @return День акции
     */
    @Column(name = "день_акции", nullable = false)
    @Id
    public short getStockDay() {
        return stockDay;
    }

    /**
     * Функция для определения дня акции
     */
    public void setStockDay(short stockDay) {
        this.stockDay = stockDay;
    }

    /**
     * @return Месяц акции
     */
    @Column(name = "месяц_акции", nullable = false)
    @Id
    public short getStockMonth() {
        return stockMonth;
    }

    /**
     * Функция для определения месяца акции
     */
    public void setStockMonth(short stockMonth) {
        this.stockMonth = stockMonth;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockEntityPK that = (StockEntityPK) o;

        if (stockDay != that.stockDay) return false;
        if (stockMonth != that.stockMonth) return false;
        if (naming != null ? !naming.equals(that.naming) : that.naming != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта StockEntityPK
     */
    @Override
    public int hashCode() {
        int result = naming != null ? naming.hashCode() : 0;
        result = 31 * result + (int) stockDay;
        result = 31 * result + (int) stockMonth;
        return result;
    }
}
