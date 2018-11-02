import dao.GenericDao;
import entities.*;

/**
 * Временный класс, носящий тестовый или демонстрационный характер.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
public class Main {
    public static void main(final String[] args) {
        GenericDao<TransportEntity, Integer> dao = new GenericDao<>(TransportEntity.class);
        /*for (TransportEntity t : dao.selectAll()) {
            System.out.println(t.getName());
        }*/
        System.out.println(dao.findById(3).getName());
        dao.finishWork();
    }
}