package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(транспорта) в виде Java-объекта.
 * @autor Арсений Порядин, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "ТРАНСПОРТ", schema = "s242419", catalog = "studs")
public class TransportEntity {
    /** Идентификатор транспорта */
    private int id;
    /** Название транспорта */
    private String name;
    /** Экологичность транспорта */
    private boolean environmentalFriendliness;
    /** Начальная квота транспорта */
    private float startingQuote;

    /** Создает новый пустой транспорт
     *  @see TransportEntity#TransportEntity(int, String, boolean, float)
     */
    public TransportEntity() {}

    /** Создает новый транспорт с заданными параметрами
     *  @see TransportEntity#TransportEntity()
     */
    public TransportEntity(int id, String name, boolean environmentalFriendliness, float startingQuote) {
        this.id = id;
        this.name = name;
        this.environmentalFriendliness = environmentalFriendliness;
        this.startingQuote = startingQuote;
    }

    /**
     * @return Идентификатор транспорта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * @return Название транспорта
     */
    @Basic
    @Column(name = "название", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    /**
     * Функция для определения названия транспорта
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Экологичность транспорта
     */
    @Basic
    @Column(name = "экологичность", nullable = false)
    public boolean isEnvironmentalFriendliness() {
        return environmentalFriendliness;
    }

    /**
     * Функция для определения экологичности транспорта
     */
    public void setEnvironmentalFriendliness(boolean environmentalFriendliness) {
        this.environmentalFriendliness = environmentalFriendliness;
    }

    /**
     * @return Начальную квоту транспорта
     */
    @Basic
    @Column(name = "нач_квота", nullable = false, precision = 0)
    public float getStartingQuote() {
        return startingQuote;
    }

    /**
     * Функция для определения начальной квоты транспорта
     */
    public void setStartingQuote(float startingQuote) {
        this.startingQuote = startingQuote;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransportEntity that = (TransportEntity) o;

        if (id != that.id) return false;
        if (environmentalFriendliness != that.environmentalFriendliness) return false;
        if (Float.compare(that.startingQuote, startingQuote) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта TransportEntity
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (environmentalFriendliness ? 1 : 0);
        result = 31 * result + (startingQuote != +0.0f ? Float.floatToIntBits(startingQuote) : 0);
        return result;
    }
}
