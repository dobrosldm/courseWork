package entities;

import javax.persistence.*;

/**
 * Класс, представляющий объект базы данных(характеристики маршрута) в виде Java-объекта.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "ХАРАКТЕРИСТИКА_МАРШРУТА", schema = "s242419", catalog = "studs")
public class RouteParamsEntity {
    /** Идентификатор маршрута */
    private int id;
    /** Время следования */
    private short timeOfPursue;
    /** Идентификатор начальной остановки */
    private int startStoppage;
    /** Идентификатор конечной остановки */
    private int finishStoppage;
    /** Общий километраж */
    private float kilometrage;
    /** Количество остановок на маршруте */
    private short stoppageCount;

    /** Создает характеристику нового маршрута без заданных параметров
     *  @see RouteParamsEntity#RouteParamsEntity(int, short, int, int, float, short)
     */
    public RouteParamsEntity() {
    }

    /** Создает характеристику нового маршрута с заданными параметрами
     *  @see RouteParamsEntity#RouteParamsEntity()
     */
    public RouteParamsEntity(int id, short timeOfPursue, int startStoppage, int finishStoppage, float kilometrage, short stoppageCount) {
        this.id = id;
        this.timeOfPursue = timeOfPursue;
        this.startStoppage = startStoppage;
        this.finishStoppage = finishStoppage;
        this.kilometrage = kilometrage;
        this.stoppageCount = stoppageCount;
    }

    /**
     * @return Идентификатор маршрута
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * @return Время следования
     */
    @Basic
    @Column(name = "время_следования", nullable = false)
    public short getTimeOfPursue() {
        return timeOfPursue;
    }

    /**
     * Функция для определения времени следования
     */
    public void setTimeOfPursue(short timeOfPursue) {
        this.timeOfPursue = timeOfPursue;
    }

    /**
     * @return Идентификатор начальной остановки
     */
    @Basic
    @Column(name = "нач_остановка", nullable = false)
    public int getStartStoppage() {
        return startStoppage;
    }

    /**
     * Функция для определения идентификатора начальной остановки
     */
    public void setStartStoppage(int startStoppage) {
        this.startStoppage = startStoppage;
    }

    /**
     * @return Идентификатор конечной остановки
     */
    @Basic
    @Column(name = "конеч_остановка", nullable = false)
    public int getFinishStoppage() {
        return finishStoppage;
    }

    /**
     * Функция для определения идентификатора конечной остановки
     */
    public void setFinishStoppage(int finishStoppage) {
        this.finishStoppage = finishStoppage;
    }

    /**
     * @return Общий километраж
     */
    @Basic
    @Column(name = "общ_километраж", nullable = false, precision = 0)
    public float getKilometrage() {
        return kilometrage;
    }

    /**
     * Функция для определения общего километража
     */
    public void setKilometrage(float kilometrage) {
        this.kilometrage = kilometrage;
    }

    /**
     * @return Количество остановок
     */
    @Basic
    @Column(name = "количество_остановок", nullable = false)
    public short getStoppageCount() {
        return stoppageCount;
    }

    /**
     * Функция для определения количества остановок
     */
    public void setStoppageCount(short stoppageCount) {
        this.stoppageCount = stoppageCount;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteParamsEntity that = (RouteParamsEntity) o;

        if (id != that.id) return false;
        if (timeOfPursue != that.timeOfPursue) return false;
        if (startStoppage != that.startStoppage) return false;
        if (finishStoppage != that.finishStoppage) return false;
        if (Float.compare(that.kilometrage, kilometrage) != 0) return false;
        if (stoppageCount != that.stoppageCount) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта RouteParamsEntity
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) timeOfPursue;
        result = 31 * result + startStoppage;
        result = 31 * result + finishStoppage;
        result = 31 * result + (kilometrage != +0.0f ? Float.floatToIntBits(kilometrage) : 0);
        result = 31 * result + (int) stoppageCount;
        return result;
    }
}
