package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(параметры транспорта) в виде Java-объекта.
 * @autor Арсений Порядин, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "ХАРАКТЕРИСТИКА_ТРАНСПОРТА", schema = "s242419", catalog = "studs")
public class TransportParamsEntity {
    /** Идентификатор транспорта */
    private int id;
    /** Максимальная скорость транспорта */
    private float maxSpeed;
    /** Средняя скорость транспорта */
    private float avgSpeed;
    /** Вид топлива транспорта */
    private String fuel;
    /** Вместительность транспорта */
    private short spaciousness;

    /** Создает новую пустую характеристику транспорта
     *  @see TransportParamsEntity#TransportParamsEntity(int, float, float, String, short)
     */
    public TransportParamsEntity() {}

    /** Создает новую характеристику транспорта с заданными параметрами
     *  @see TransportParamsEntity#TransportParamsEntity()
     */
    public TransportParamsEntity(int id, float maxSpeed, float avgSpeed, String fuel, short spaciousness) {
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.avgSpeed = avgSpeed;
        this.fuel = fuel;
        this.spaciousness = spaciousness;
    }

    /**
     * @return Идентификатор транспорта
     */
    @Id
    @Column(name = "ид", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Функция для определения идентификатора транспорта
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Максимальную скорость транспорта
     */
    @Basic
    @Column(name = "макс_скорость", nullable = false, precision = 0)
    public float getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Функция для определения максимальной скорости транспорта
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * @return Среднюю скорость транспорта
     */
    @Basic
    @Column(name = "сред_скорость", nullable = false, precision = 0)
    public float getAvgSpeed() {
        return avgSpeed;
    }

    /**
     * Функция для определения средней скорости транспорта
     */
    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    /**
     * @return Вид топлива транспорта
     */
    @Basic
    @Column(name = "топливо", nullable = false, length = -1)
    public String getFuel() {
        return fuel;
    }

    /**
     * Функция для определения вида топлива транспорта
     */
    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    /**
     * @return Вместительность транспорта
     */
    @Basic
    @Column(name = "вместительность", nullable = false)
    public short getSpaciousness() {
        return spaciousness;
    }

    /**
     * Функция для определения вместительности транспорта
     */
    public void setSpaciousness(short spaciousness) {
        this.spaciousness = spaciousness;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransportParamsEntity that = (TransportParamsEntity) o;

        if (id != that.id) return false;
        if (Float.compare(that.maxSpeed, maxSpeed) != 0) return false;
        if (Float.compare(that.avgSpeed, avgSpeed) != 0) return false;
        if (spaciousness != that.spaciousness) return false;
        if (fuel != null ? !fuel.equals(that.fuel) : that.fuel != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта TransportParamsEntity
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (maxSpeed != +0.0f ? Float.floatToIntBits(maxSpeed) : 0);
        result = 31 * result + (avgSpeed != +0.0f ? Float.floatToIntBits(avgSpeed) : 0);
        result = 31 * result + (fuel != null ? fuel.hashCode() : 0);
        result = 31 * result + (int) spaciousness;
        return result;
    }
}
