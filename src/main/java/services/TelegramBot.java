package services;

import dao.TodayLimitDao;
import dao.UserDao;
import entities.UserEntity;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.hasText() && message.getText().toLowerCase().startsWith("почта:"))
            regUser(message);
        else if (message.hasText() && message.getText().startsWith("/limits"))
            showLimits(message);
        else checkEmail(message);
    }

    private void checkEmail(Message msg) {
        UserDao userDao = new UserDao();
        UserEntity userEntity;
        boolean alreadyExists = true;
        try {
            userEntity = userDao.findByTelegramId(msg.getFrom().getId());
        } catch (NoResultException e) {
            alreadyExists = false;
        }

        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setParseMode("HTML");

        if (alreadyExists) {
            s.setText("Приветствую тебя вновь, пользователь футуристичного транспорта!\n" +
                    "Напоминаю, что ты автоматически получишь уведомления о важных событиях в этот уютный чатик при их" +
                    " наличии!");
            try {
                execute(s);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        } else {
            s.setText("Приветствую тебя, пользователь футуристичного транспорта!\n" +
                    "Если ты уже пользуешься услугами нашего сервиса, то скорее сообщи мне свою электронную почту " +
                    "в формате <i>\"почта: &ltтвоя почта&gt\"</i>!\n" +
                    "Если же нет - беги регистрируйся в сервисе и возвращайся сюда!");
            try {

                execute(s);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }

    }

    private void regUser(Message msg) {
        String email = msg.getText().split(" ")[1];
        UserDao userDao = new UserDao();
        UserEntity userEntity = null;
        boolean exists;
        try {
            userEntity = userDao.findByEmail(email);
            exists = true;
        } catch (NoResultException e) {
            exists = false;
        }

        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setParseMode("HTML");

        if (exists) {
            if (userEntity.getTelegramId() != null) {
                s.setText("Не балуйся, я тебя уже знаю \uD83D\uDE1C");
                try {
                    execute(s);
                } catch (TelegramApiException e){
                    e.printStackTrace();
                }
            } else {
                userEntity.setTelegramId(msg.getFrom().getId());
                try {
                    userDao.update(userEntity);
                } catch (RollbackException e) {
                    s.setText("Не надо так... Твой телеграмм аккаунт уже привязан к вот этой почте: <b>" +
                            userDao.findByTelegramId(msg.getFrom().getId()).getEmail() + "</b>");
                    try {
                        execute(s);
                        return;
                    } catch (TelegramApiException telErr){
                        telErr.printStackTrace();
                        return;
                    }
                }
                s.setText("Добро пожаловать, " + userEntity.getName() + "!\n" +
                        "Теперь ты по-настоящему окунаешься в <i>мир будущего</i> " +
                        "с нашим футуристичным навигатором!\n" +
                        "В этот уютный чатик ты будешь автоматически получать уведомления о важных событиях при их" +
                        ", конечно же, наличии!");
                try {
                    execute(s);
                } catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }
        } else {
            s.setText("Пользователь с таким адресом не зарегестрирован в нашем сервисе...");
            try {
                execute(s);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    private void showLimits(Message msg) {
        UserDao userDao = new UserDao();
        UserEntity userEntity;

        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setParseMode("HTML");

        try {
            userEntity = userDao.findByTelegramId(msg.getFrom().getId());
        } catch (NoResultException e) {
            s.setText("Сначала тебе нужно поделиться со мной своей почтой " +
                    "в формате <i>\"почта: &ltтвоя почта&gt\"</i>");
            try {
                execute(s);
            } catch (TelegramApiException telErr){
                telErr.printStackTrace();
            }
            return;
        }

        TodayLimitDao limitDao = new TodayLimitDao();
        StringBuilder stringBuilder = new StringBuilder("У тебя осталось следующее количество лимитов:");
        for (Object[] limit:
                limitDao.findByUserId(userEntity.getId())) {
            stringBuilder.append("\n<i>").append(limit[0].toString()).append(":</i> <b>")
                    .append(limit[1].toString()).append(" ед.</b>");
        }
        s.setText(stringBuilder.toString());
        try {
            execute(s);
        } catch (TelegramApiException telErr){
            telErr.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "FutureNavigatorBot";
    }

    @Override
    public String getBotToken() {
        return "636965547:AAFnjGBA5tyybFjFFEyaoNA8NIVFw1GEWII";
    }
}
