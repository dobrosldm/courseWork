import dao.GenericDao;
import entities.*;

import java.util.logging.Level;

/**
 * Временный класс, носящий тестовый или демонстрационный характер.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
public class Main {
    public static void main(final String[] args) {
        GenericDao<FuelEntity, Integer> dao = new GenericDao<>(FuelEntity.class);
        System.out.println(dao.selectAll().get(3).getId().getNaming());
        dao.finishWork();
    }
}