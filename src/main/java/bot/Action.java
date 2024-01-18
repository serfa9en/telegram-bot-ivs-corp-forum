package bot;

import based.UserBase;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import constant.Constant;

public class Action {
    private TelegramBot bot;
    private Update update;
    private Long chatId;
    private UserBase userDB;
    Constant constant = new Constant();

    public Action (TelegramBot tbot) {
        this.bot = tbot;
    }

    public void setUpdate(Update update) {
        this.update = update;
        if (this.update.message() != null)  chatId = this.update.message().chat().id();
    }

    public void createDataBase(String fileUser) {
        userDB = new UserBase(fileUser);
    }

    // Установка флага действия
    public void setAction(String userId, String flag) {
        userDB.setFlag(userId, flag);
    }

    // проверка, есть ли такой пользователь в базе
    public boolean check(String userId) {
        return userDB.checkUser(userId);
    }

    // добавляем информацию о пользователе в файл
    public void createNewUser(String userId) {
        String[] str = new String[5];
        str[0] = userId;
        str[1] = update.message().from().firstName();
        str[2] = update.message().from().lastName();
        str[3] = update.message().from().username();
        str[4] = "";
        userDB.addNewUser(str);
    }

    // добавляем ФИО
    private void registration (String userId) {
        userDB.addName(userId, update.message().text());
        setAction(userId, constant.USER_FLAGS_DEFAULT);
        bot.execute(new SendMessage(userId, getCardUser(userId)));
        sendMenu(userId);
    }

    // Что делаем при дефолтной строки
    public void doDefault(String userId) {
        // проходим регистрацию
        if (userDB.getFlag(userId).equals(constant.USER_FLAGS_REGISTRY)) {
            registration(userId);
        }
    }

    // Вывод карточки пользователя
    public String getCardUser(String userId) {
        String str = "\uD83D\uDCCC ИНФОРМАЦИЯ\n\n";
        str += "Имя: " + userDB.getData(userId, 4) + "\n\n";;
//        str += "✏\uFE0F Если Вы хотите изменить ФИО], введите /edit\n\n";
        str += "Здесь будет история вопросов\n\n";
        str += "А здесь - записи на круглый стол\n";
        return str;
    }

    // собираем меню из кнопок
    public void sendMenu(String userId) {

    }
}
