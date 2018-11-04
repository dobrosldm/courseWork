import dao.GenericDao;
import dao.UserDao;
import entities.*;

import java.util.logging.Level;

/**
 * Временный класс, носящий тестовый или демонстрационный характер.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
public class Main {
    public static void main(final String[] args) {
        UserDao dao = new UserDao();
        System.out.println(dao.findByEmail("arsewewe_bakaeva@teodorovna.ru").getName());
        dao.finishWork();
    }
}