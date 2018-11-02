package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(лимит на сегодня) в виде Java-объекта.
 * @autor Арсений Порядин, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "ЛИМИТ_НА_СЕГОДНЯ", schema = "s242419", catalog = "studs")
@IdClass(TodayLimitEntityPK.class)
public class TodayLimitEntity {
    /** Идентификатор пользователя */
    private int userId;
    /** Идентификатор транспорта */
    private int transportId;
    /** Лимит по транспорту */
    private Float limit;

    /** Создает новый пустой лимит на сегодня
     *  @see TodayLimitEntity#TodayLimitEntity(int, int, Float)
     */
    public TodayLimitEntity() {}

    /** Создает новый лимит на сегодня с заданными параметрами
     *  @see TodayLimitEntity#TodayLimitEntity()
     */
    public TodayLimitEntity(int userId, int transportId, Float limit) {
        this.userId = userId;
        this.transportId = transportId;
        this.limit = limit;
    }

    /**
     * @return Идентификатор пользователя
     */
    @Id
    @Column(name = "ид_пользователя", nullable = false)
    public int getUserId() {
        return userId;
    }

    /**
     * Функция для определения идентификатора пользователя
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return Идентификатор транспорта
     */
    @Id
    @Column(name = "ид_транспорта", nullable = false)
    public int getTransportId() {
        return transportId;
    }

    /**
     * Функция для определения идентификатора транспорта
     */
    public void setTransportId(int transportId) {
        this.transportId = transportId;
    }

    /**
     * @return Лимит по транспорту
     */
    @Basic
    @Column(name = "лимит", nullable = true, precision = 0)
    public Float getLimit() {
        return limit;
    }

    /**
     * Функция для определения лимита по транспорту
     */
    public void setLimit(Float limit) {
        this.limit = limit;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodayLimitEntity that = (TodayLimitEntity) o;

        if (userId != that.userId) return false;
        if (transportId != that.transportId) return false;
        if (limit != null ? !limit.equals(that.limit) : that.limit != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта TodayLimitEntity
     */
    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + transportId;
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        return result;
    }
}
