package entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ConverterEntityPK implements Serializable {
    /** Идентификатор первого вида транспорта */
    private int transport1;
    /** Идентификатор второго вида транспорта */
    private int transport2;

    public ConverterEntityPK() {
    }

    public ConverterEntityPK(int transport1, int transport2) {
        this.transport1 = transport1;
        this.transport2 = transport2;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConverterEntityPK)) return false;
        ConverterEntityPK that = (ConverterEntityPK) o;
        return getTransport1() == that.getTransport1() &&
                getTransport2() == that.getTransport2();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTransport1(), getTransport2());
    }
}
