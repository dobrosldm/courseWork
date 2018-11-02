package entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ConverterEntityKey implements Serializable {
    /** Идентификатор первого вида транспорта */
    private int transport1;
    /** Идентификатор второго вида транспорта */
    private int transport2;
    /** Коэффициент конвертации */
    private float ratio;

    /**
     * @return Идентификатор первого вида транспорта
     */
    @Basic
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
    @Basic
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


    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConverterEntityKey that = (ConverterEntityKey) o;

        if (transport1 != that.transport1) return false;
        if (transport2 != that.transport2) return false;
        if (Float.compare(that.ratio, ratio) != 0) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта ConverterEntity
     */
    @Override
    public int hashCode() {
        int result = transport1;
        result = 31 * result + transport2;
        result = 31 * result + (ratio != +0.0f ? Float.floatToIntBits(ratio) : 0);
        return result;
    }
}
