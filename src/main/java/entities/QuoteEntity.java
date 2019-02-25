package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(квоты) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "КВОТА", schema = "s242419", catalog = "studs")
public class QuoteEntity {
    /** Идентификатор квоты */
    private int id;
    /** Соответствующая надбавка */
    private float addition;

    /** Создает новую квоту без заданных параметров
     *  @see QuoteEntity#QuoteEntity(int, float)
     */
    public QuoteEntity() {
    }

    /** Создает новую квоту без заданных параметров
     *  @see QuoteEntity#QuoteEntity()
     */
    public QuoteEntity(int id, float addition) {
        this.id = id;
        this.addition = addition;
    }

    /**
     * @return Идентификатор квоты
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ид", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Функция для определения идентификатора квоты
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Размер надбавки
     */
    @Basic
    @Column(name = "надбавка", nullable = false, precision = 0)
    public float getAddition() {
        return addition;
    }

    /**
     * Функция для определения размера надбавки
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

        QuoteEntity that = (QuoteEntity) o;

        if (id != that.id) return false;
        if (Float.compare(that.addition, addition) != 0) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта QuoteEntity
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (addition != +0.0f ? Float.floatToIntBits(addition) : 0);
        return result;
    }
}
