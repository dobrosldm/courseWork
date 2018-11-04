package entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс, представляющий объект базы данных(конвертера лимитов) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "КОНВЕРТЕР", schema = "s242419", catalog = "studs")
@IdClass(ConverterEntityPK.class)
public class ConverterEntity {
    /** Идентификатор первого вида транспорта */
    private int transport1;
    /** Идентификатор второго вида транспорта */
    private int transport2;
    /** Коэффициент конвертации */
    private float ratio;

    public ConverterEntity() {
    }

    public ConverterEntity(int transport1, int transport2, float ratio) {
        this.transport1 = transport1;
        this.transport2 = transport2;
        this.ratio = ratio;
    }

    /**
     * @return Идентификатор первого вида транспорта
     */
    @Id
    @Column(name = "транс_1", nullable = false)
    public int getTransport1() {
        return transport1;
    }

    /**
     * Функция для определения идентификатора первого вида транспорта
     */
    public void setTransport1(int transport1) {
        this.transport1 = transport1;
    }

    /**
     * @return Идентификатор второго вида транспорта
     */
    @Id
    @Column(name = "транс_2", nullable = false)
    public int getTransport2() {
        return transport2;
    }

    /**
     * Функция для определения идентификатора второго вида транспорта
     */
    public void setTransport2(int transport2) {
        this.transport2 = transport2;
    }

    /**
     * @return Значение коэффициента конвертации
     */
    @Basic
    @Column(name = "коэффициент", nullable = false, precision = 0)
    public float getRatio() {
        return ratio;
    }

    /**
     * Функция для определения значения коэффициента конвертации
     */
    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConverterEntity)) return false;
        ConverterEntity that = (ConverterEntity) o;
        return getTransport1() == that.getTransport1() &&
                getTransport2() == that.getTransport2() &&
                Float.compare(that.getRatio(), getRatio()) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTransport1(), getTransport2(), getRatio());
    }
}
