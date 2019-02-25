package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(остановки) в виде Java-объекта.
 * @autor Арсений Порядин, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "ОСТАНОВКА", schema = "s242419", catalog = "studs")
public class StoppageEntity {
    /** Идентификатор остановки */
    private int id;
    /** Название остановки */
    private String name;
    /** Идентификатор следующей остановки */
    private Integer nextId;
    /** Идентификатор предыдущей остановки */
    private Integer prevId;
    /** Идентификатор маршрута */
    private Integer routeId;
    /** Координата Х */
    private Integer xCoord;
    /** Координата У */
    private Integer yCoord;

    /** Создает новую пустую остановку
     *  @see StoppageEntity#StoppageEntity(int, String, Integer, Integer, Integer, Integer, Integer)
     */
    public StoppageEntity() {}

    /** Создает новую остановку с заданными параметрами
     *  @see StoppageEntity#StoppageEntity()
     */
    public StoppageEntity(int id, String name, Integer nextId, Integer prevId, Integer routeId, Integer xCoord, Integer yCoord) {
        this.id = id;
        this.name = name;
        this.nextId = nextId;
        this.prevId = prevId;
        this.routeId = routeId;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * @return Идентификатор остановки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ид", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Функция для определения идентификатора остановки
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Название остановки
     */
    @Basic
    @Column(name = "название", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    /**
     * Функция для определения названия остановки
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return ID следующей остановки
     */
    @Basic
    @Column(name = "след_ид", nullable = true)
    public Integer getNextId() {
        return nextId;
    }

    /**
     * Функция для определения идентификатора слкдующей остановки
     */
    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }

    /**
     * @return ID предыдущей остановки
     */
    @Basic
    @Column(name = "пред_ид", nullable = true)
    public Integer getPrevId() {
        return prevId;
    }

    /**
     * Функция для определения идентификатора предыдущей остановки
     */
    public void setPrevId(Integer prevId) {
        this.prevId = prevId;
    }

    /**
     * @return ID маршрута
     */
    @Basic
    @Column(name = "ид_маршрута", nullable = true)
    public Integer getRouteId() {
        return routeId;
    }

    /**
     * Функция для определения идентификатора маршрута
     */
    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    /**
     * @return Х координату
     */
    @Basic
    @Column(name = "х_коор", nullable = true)
    public Integer getXCoord() {
        return xCoord;
    }

    /**
     * Функция для определения Х координаты
     */
    public void setXCoord(Integer xCoord) {
        this.xCoord = xCoord;
    }

    /**
     * @return У координату
     */
    @Basic
    @Column(name = "у_коор", nullable = true)
    public Integer getYCoord() {
        return yCoord;
    }

    /**
     * Функция для определения У координаты
     */
    public void setYCoord(Integer yCoord) {
        this.yCoord = yCoord;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoppageEntity that = (StoppageEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nextId != null ? !nextId.equals(that.nextId) : that.nextId != null) return false;
        if (prevId != null ? !prevId.equals(that.prevId) : that.prevId != null) return false;
        if (routeId != null ? !routeId.equals(that.routeId) : that.routeId != null) return false;
        if (xCoord != null ? !xCoord.equals(that.xCoord) : that.xCoord != null) return false;
        if (yCoord != null ? !yCoord.equals(that.yCoord) : that.yCoord != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта StoppageEntity
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nextId != null ? nextId.hashCode() : 0);
        result = 31 * result + (prevId != null ? prevId.hashCode() : 0);
        result = 31 * result + (routeId != null ? routeId.hashCode() : 0);
        result = 31 * result + (xCoord != null ? xCoord.hashCode() : 0);
        result = 31 * result + (yCoord != null ? yCoord.hashCode() : 0);
        return result;
    }
}
