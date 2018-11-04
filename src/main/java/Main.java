import beans.ConverterBean;
import beans.LoginBean;
import dao.GenericDao;
import dao.UserDao;
import entities.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.logging.Level;

/**
 * Временный класс, носящий тестовый или демонстрационный характер.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
public class Main {
    public static void main(final String[] args) {
        ConverterBean converterBean = new ConverterBean();
        System.out.println(converterBean.convert(1, 2, 15));
    }
}