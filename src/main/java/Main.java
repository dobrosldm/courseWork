import beans.LimitsBean;
import dao.TodayLimitDao;
import entities.TodayLimitEntity;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.TelegramBot;

/**
 * Временный класс, носящий тестовый или демонстрационный характер.
 * @autor Порядин Арсений, Добровицкий Дмитрий
 * @version 1.0
 */
public class Main {
    public static void main(final String[] args) {
        /*TodayLimitDao limitDao = new TodayLimitDao();
        for (Object[] limit :
                limitDao.findByUserId(10014)) {
            System.out.println(limit[0] + " " + limit[1]);
        }
        limitDao.finishWork();*/
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "socksProxyHost", "157.230.56.56" );
        System.getProperties().put( "socksProxyPort", "1080" );
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}