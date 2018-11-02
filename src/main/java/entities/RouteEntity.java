package entities;

import javax.persistence.*;
import java.sql.Time;

/**
 * Класс, представляющий объект базы данных(маршрут) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "МАРШРУТ", schema = "s242419", catalog = "studs")
public class RouteEntity {
    /** Идентификатор маршрута */
    private int id;
    /** Номер маршрута */
    private Integer number;
    /** Идентификатор соответствующего транспорта */
    private Integer transportId;
    /** Периодичность */
    private short periodicity;
    /** Время начала работы маршрута */
    private Time startOfWork;
    /** Время окончания работы маршрута */
    private Time endOfWork;

    /** Создает новый маршрут без заданных параметров
     *  @see RouteEntity#RouteEntity(int, Integer, Integer, short, Time, Time)
     */
    public RouteEntity() {
    }

    /** Создает новый маршрут с заданными параметрами
     *  @see RouteEntity#RouteEntity()
     */
    public RouteEntity(int id, Integer number, Integer transportId, short periodicity, Time startOfWork, Time endOfWork) {
        this.id = id;
        this.number = number;
        this.transportId = transportId;
        this.periodicity = periodicity;
        this.startOfWork = startOfWork;
        this.endOfWork = endOfWork;
    }

    /**
     * @return Идентификатор маршрута
     */
    @Id
    @Column(name = "ид", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Функция для определения идентификатора маршрута
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Номер маршрута
     */
    @Basic
    @Column(name = "номер", nullable = true)
    public Integer getNumber() {
        return number;
    }

    /**
     * Функция для определения номера маршрута
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * @return Идентификатор соответствующего транспорта
     */
    @Basic
    @Column(name = "ид_транспорта", nullable = true)
    public Integer getTransportId() {
        return transportId;
    }

    /**
     * Функция для определения идентификатора соответствующего транспорта
     */
    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    /**
     * @return Периодичность
     */
    @Basic
    @Column(name = "периодичность", nullable = false)
    public short getPeriodicity() {
        return periodicity;
    }

    /**
     * Функция для определения периодичности
     */
    public void setPeriodicity(short periodicity) {
        this.periodicity = periodicity;
    }

    /**
     * @return Время начала работы маршрута
     */
    @Basic
    @Column(name = "нач_работы", nullable = false)
    public Time getStartOfWork() {
        return startOfWork;
    }

    /**
     * Функция для определения времени начала работы маршрута
     */
    public void setStartOfWork(Time startOfWork) {
        this.startOfWork = startOfWork;
    }

    /**
     * @return Время окончания работы маршрута
     */
    @Basic
    @Column(name = "оконч_работы", nullable = false)
    public Time getEndOfWork() {
        return endOfWork;
    }

    /**
     * Функция для определения времени окончания работы маршрута
     */
    public void setEndOfWork(Time endOfWork) {
        this.endOfWork = endOfWork;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteEntity that = (RouteEntity) o;

        if (id != that.id) return false;
        if (periodicity != that.periodicity) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (transportId != null ? !transportId.equals(that.transportId) : that.transportId != null) return false;
        if (startOfWork != null ? !startOfWork.equals(that.startOfWork) : that.startOfWork != null) return false;
        if (endOfWork != null ? !endOfWork.equals(that.endOfWork) : that.endOfWork != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта RouteEntity
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (transportId != null ? transportId.hashCode() : 0);
        result = 31 * result + (int) periodicity;
        result = 31 * result + (startOfWork != null ? startOfWork.hashCode() : 0);
        result = 31 * result + (endOfWork != null ? endOfWork.hashCode() : 0);
        return result;
    }
}
