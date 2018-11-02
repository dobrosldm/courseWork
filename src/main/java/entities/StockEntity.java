package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(акции) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "АКЦИЯ", schema = "s242419", catalog = "studs")
@IdClass(StockEntityPK.class)
public class StockEntity {
    /** Название акции */
    private String naming;
    /** День проведения акции */
    private short stockDay;
    /** Месяц проведения акции */
    private short stockMonth;
    /** Соответствующая надбавка */
    private float addition;

    /** Создает новую акцию без заданных параметров
     *  @see StockEntity#StockEntity(String, short, short, float)
     */
    public StockEntity() {
    }

    /** Создает новую акцию с заданными параметрами
     *  @see StockEntity#StockEntity()
     */
    public StockEntity(String naming, short stockDay, short stockMonth, float addition) {
        this.naming = naming;
        this.stockDay = stockDay;
        this.stockMonth = stockMonth;
        this.addition = addition;
    }

    /**
     * @return Название акции
     */
    @Id
    @Column(name = "название", nullable = false, length = -1)
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
    @Id
    @Column(name = "день_акции", nullable = false)
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
    @Id
    @Column(name = "месяц_акции", nullable = false)
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
     * @return Соответствующая надбавка
     */
    @Basic
    @Column(name = "надбавка", nullable = false, precision = 0)
    public float getAddition() {
        return addition;
    }

    /**
     * Функция для определения соответствующей надбавки
     */
    public void setAddition(float addition) {
        this.addition = addition;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockEntity that = (StockEntity) o;

        if (stockDay != that.stockDay) return false;
        if (stockMonth != that.stockMonth) return false;
        if (Float.compare(that.addition, addition) != 0) return false;
        if (naming != null ? !naming.equals(that.naming) : that.naming != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта StockEntity
     */
    @Override
    public int hashCode() {
        int result = naming != null ? naming.hashCode() : 0;
        result = 31 * result + (int) stockDay;
        result = 31 * result + (int) stockMonth;
        result = 31 * result + (addition != +0.0f ? Float.floatToIntBits(addition) : 0);
        return result;
    }
}
