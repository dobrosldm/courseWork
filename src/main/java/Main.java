import dao.HibernateDao;
import entities.*;

/**
 * Временный класс, носящий тестовый или демонстрационный характер.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
public class Main {
    public static void main(final String[] args) {
        HibernateDao<TransportEntity, Integer> dao = new HibernateDao<>(TransportEntity.class);
        TransportEntity transportEntity = new TransportEntity(6, "ывалгпи", false, 100);
        dao.update(transportEntity);
        System.exit(0);
    }
}