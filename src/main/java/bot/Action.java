package bot;

import based.UserBase;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
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
        int messageId = update.message().messageId() + 1;
        sendMenu(userId, messageId);
    }

    // Что делаем при дефолтной строки
    public void doDefault(String userId) {
        // проходим регистрацию
        if (userDB.getFlag(userId).equals(constant.USER_FLAGS_REGISTRY)) {
            registration(userId);
        }
    }

    public String getData(String userId, int i) {
        return userDB.getData(userId, i);
    }

    // Вывод карточки пользователя
    public String getCardUser(String userId) {
        String str = "\uD83D\uDCCC ИНФОРМАЦИЯ\n\n";
        str += "Имя: " + userDB.getData(userId, 4) + "\n\n";;
//        str += "✏\uFE0F Если Вы хотите изменить ФИО], введите /edit\n\n";
        str += "Здесь будет история вопросов\n\n";
        str += getUserTables(userId);
        return str;
    }

    // собираем меню из кнопок
    public void sendMenu(String userId, int messageId) {

        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Программа мероприятия");
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_PROGRAM);
        inlineKeyboardButtons0[0].url("https://telegra.ph/Mezhregionalnyj-cifrovoj-forum-Obmen-opytom-vnedreniya-i-ehkspluatacii-Rossijskih-produktov-01-23");

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton("Досье спикеров");
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_SPEAKERS);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton("Задать вопрос");
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_QUEST);

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton("Записаться на круглый стол");
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_TABLE);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3
        );


        bot.execute(new SendMessage(userId, "Главное меню:")
                .replyMarkup(inlineKeyboard));

    }
    
    // досье спикеров
    public void createSpeakers (String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Информационная безопасность");
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKERS_SECURITY);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton("Информационные технологии ");
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKERS_TECNOLOGY);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1
        );

        bot.execute(new EditMessageText (userId, messageId, "Выберите секцию:")
                .replyMarkup(inlineKeyboard));
    }

    //задать вопрос
    public void createQuest (String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Информационная безопасность");
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.QUEST_SECURITY);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton("Информационные технологии");
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.QUEST_TECNOLOGY);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton("Пленарное заседание");
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.QUEST_PLENARY);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2
        );

        bot.execute(new EditMessageText (userId, messageId, "Выберите секцию:")
                .replyMarkup(inlineKeyboard));
    }

    // круглый стол
    public void createTable (String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Цифровая Россия");
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.TABLE_1);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton("АйТи БАСТИОН");
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.TABLE_2);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton("СДИ Софт");
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.TABLE_3);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2
        );

        bot.execute(new EditMessageText (userId, messageId, "\uD83D\uDCCD КРУГЛЫЕ СТОЛЫ\n" +
                "\n" +
                "\uD83D\uDCCE 13:00 - 15:00 — Цифровая Россия\n" +
                "Зал Edison\n" +
                "    • Депутат Государственной Думы, координатор федерального партпроекта «Цифровая Россия» Немкин А.И.; \n" +
                "    • Генеральный директор фонда «Цифровая Долина Прикамья», руководитель федерального штаба партийного проекта «Цифровая Россия» Ландарь А.С.\n" +
                "    • Министр ЦР Республики Калмыкия Этеев А.П.\n" +
                "    • Министр ЦР, ИТ и связей Рязанской области Ульянов А.Ю. \n" +
                "    • Министр ЦР и информационно-коммуникационных технологий Новгородской области Киблер М.В.\n" +
                "    • Руководитель направления развития Ассоциации социальных предпринимателей Астраханской области Махринский С.В.\n" +
                "\n" +
                "\uD83D\uDCCE 15:30 - 18:45 — АйТи БАСТИОН\n" +
                "Зал Edison\n" +
                "«АйТи Бастион» — производитель системы контроля действий поставщиков ИТ-услуг СКДПУ НТ." +
                "Компания занимает более 50% российского рынка PAM-решений (Privileged Access Management). " +
                "СКДПУ НТ является единственной на данный момент PAM-системой, сертифицированной ФСТЭК России. " +
                "Отечественный разработчик решений в области информационной безопасности.\n" +
                "\n" +
                "\uD83D\uDCCE 16:45 - 17:30 — СДИ Софт\n" +
                "Информационная безопасность\n" +
                "Евгений Кривоносов, Генеральный директор ООО «СДИ СОФТ»" + getUserTables(userId))
                .replyMarkup(inlineKeyboard));
    }

    public boolean notTable (String userId, String text) {
        return userDB.isUserTable(userId, text);
    }

    private String getUserTables(String userId) {
        String str = "";
        if (userDB.isTable(userId)) {
            // есть хотя бы одна запись на круглый стол
            str += "\n\n\uD83D\uDD34 ВАШИ ЗАПИСИ:\n";
            str += userDB.getTables(userId);
        }
        return str;
    }

    public void setUserTable (String userId, String text) {
        userDB.setTable(userId,text);
    }

    public void createSecuritySpeakers (String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Алтухов-В");
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_SECURITY_1);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton("Владимир Богачев_РЕД Софт");
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_SECURITY_2);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton("Дятлов Иван Михайлович Конфидент");
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_SECURITY_3);

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton("Ландырь А");
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_SECURITY_4);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3
        );

        bot.execute(new EditMessageText (userId, messageId, "Спикеры секции 'Информационная безопасность'")
                .replyMarkup(inlineKeyboard));
    }

    public void createTecnologySpeakers (String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Михаил Самсонов_PT");
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_TECNOLOGY_1);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton("Немкин А.И.");
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_TECNOLOGY_2);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton("Фото_Кривоносов Е.Н._СДИ Софт");
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_TECNOLOGY_3);

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton("Шиловских Петр Александрович");
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_TECNOLOGY_4);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3
        );

        bot.execute(new EditMessageText (userId, messageId, "Спикеры секции 'Информационные технологии'")
                .replyMarkup(inlineKeyboard));
    }
}
