package entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Класс, представляющий множественный первичный ключ для объекта базы данных(лимит на сегодня) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
public class TodayLimitEntityPK implements Serializable {
    /** Идентификатор пользователя */
    private int userId;
    /** Идентификатор транспорта */
    private int transportId;

    public TodayLimitEntityPK() {
    }

    /** Создает связку ключей для ежедневного лимита с заданными параметрами */
    public TodayLimitEntityPK(int userId, int transportId) {
        this.userId = userId;
        this.transportId = transportId;
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
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodayLimitEntityPK that = (TodayLimitEntityPK) o;

        if (userId != that.userId) return false;
        if (transportId != that.transportId) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта TodayLimitEntityPK
     */
    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + transportId;
        return result;
    }
}
