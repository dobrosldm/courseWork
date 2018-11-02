package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(льготы) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "ЛЬГОТА", schema = "s242419", catalog = "studs")
public class BenefitEntity {
    /** Идентификатор льготы */
    private int id;
    /** Название льготы */
    private String naming;
    /** Идентификатор соответствующей квоты */
    private int quoteId;

    /** Создает новую льготу без заданных параметров
     *  @see BenefitEntity#BenefitEntity(int, String, int)
     */
    public BenefitEntity() {
    }

    /** Создает новую льготу с заданными параметрами
     *  @see BenefitEntity#BenefitEntity()
     */
    public BenefitEntity(int id, String naming, int quoteId) {
        this.id = id;
        this.naming = naming;
        this.quoteId = quoteId;
    }

    /**
     * @return Идентификатор льготы
     */
    @Id
    @Column(name = "ид", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Функция для определения идентификатора льготы
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Название льготы
     */
    @Basic
    @Column(name = "название", nullable = false, length = -1)
    public String getNaming() {
        return naming;
    }

    /**
     * Функция для определения названия льготы
     */
    public void setNaming(String naming) {
        this.naming = naming;
    }

    /**
     * @return Идентификатор соответствующей квоты
     */
    @Basic
    @Column(name = "ид_квоты", nullable = false)
    public int getQuoteId() {
        return quoteId;
    }

    /**
     * Функция для определения идентификатора соответствующей квоты
     */
    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenefitEntity that = (BenefitEntity) o;

        if (id != that.id) return false;
        if (quoteId != that.quoteId) return false;
        if (naming != null ? !naming.equals(that.naming) : that.naming != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта BenefitEntity
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (naming != null ? naming.hashCode() : 0);
        result = 31 * result + quoteId;
        return result;
    }
}
