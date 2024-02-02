package bot;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.EditMessageText;
import dataBased.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import properties.Buttons;
import properties.Constant;
import properties.Logging;

public class Action {
    private TelegramBot bot;
    private Update update;
    private DataBased userDB;
    private DataBased adminDB;
    private DataBased moderatorDB;
    private DataBased speakerDB;

    private DataBased questions;
    private DataBased tables;
    private Logging logging;

    Buttons buttons;
    Constant constant = new Constant();

    public Action (TelegramBot tbot) {
        this.bot = tbot;
        buttons = new Buttons(bot);
    }

    public void setUpdate(Update update) {
        this.update = update;
        buttons.setUpdate(update);
        //if (this.update.message() != null)  chatId = this.update.message().chat().id();
    }

    public void createDataBase(String fileUser) {
        userDB = new User(fileUser);
        adminDB = new Admin(fileUser);
        moderatorDB = new Moderator(fileUser);
        speakerDB = new Speaker(fileUser);
        questions = new Question(fileUser);
        tables = new Table(fileUser);
        buttons.setFile(fileUser);
        logging= new Logging(fileUser);
    }

    public boolean checkIs (String userId) {
        return userDB.isPerson(userId);
    }

    // добавляем информацию о пользователе в файл
    public void createNewUser(String userId) {
        userDB.setData(userId, userId, 0);
        userDB.setData(userId, update.message().from().firstName(), 1);
        userDB.setData(userId, update.message().from().lastName(), 2);
        userDB.setData(userId, update.message().from().username(), 3);
        userDB.setData(userId, "", 4);
        userDB.setData(userId, "", 5);
    }

    // проверка кто перед нами:
    // спикер, модератора, админ или пользователь
    public int isPerson(String userId) {
        // 1 - Admin
        // 2 - Moderator
        // 3 - Speaker
        // 4 - User

        int id = 4;
        if (adminDB.isPerson(userId)) id = 1;
        if (moderatorDB.isPerson(userId)) id = 2;
        if (speakerDB.isPerson(userId)) id = 3;
        if (userDB.isPerson(userId)) id = 4;

        return id;
    }


    // Установка флага действия
    public void setAction(String userId, String flag) {
        userDB.setFlag(userId, flag);
    }

    public boolean checkName(String userId) {
        return userDB.checkUser(userId);
    }

    // Что делаем при дефолтной строки
    public void doDefault(String userId) {
        // проходим регистрацию
        if (userDB.getFlag(userId).equals(constant.USER_FLAGS_REGISTRY)) {
            registration(userId);
        }

        // задаем вопрос
        if (userDB.getFlag(userId).equals(constant.USER_FLAGS_QUESTION_PLENARY)) {
            setAction(userId,constant.USER_FLAGS_DEFAULT);
            buttons.askQuestion(userId);
            // System.out.println("HH");
        }
    }

    // добавляем ФИО
    private void registration (String userId) {
        userDB.setData(userId, update.message().text(),4);
        setAction(userId, constant.USER_FLAGS_DEFAULT);
        sendCardUser(userId);
        sendMenu(userId);
    }

    public void sendCardUser(String userId) {
        int messageId = update.message().messageId() + 1;
        InlineKeyboardMarkup inlineKeyboard = buttons.getButtonsCardUser(userId, messageId);

        bot.execute(new SendMessage(userId, getCardUser(userId))
                .parseMode(ParseMode.HTML)
                .replyMarkup(inlineKeyboard));
    }

        // Вывод карточки пользователя
    public String getCardUser(String userId) {
        String str = "\uD83D\uDCCC <b>ИНФОРМАЦИЯ</b>\n\n";
        str += userDB.getData(userId, 4) + "\n\n";
        str += "➖➖➖\n";
        str += getUserQuestions(userId);
        str += getUserTables(userId);
        return str;
    }

    public void sendMenu (String userId) {
        int messageId = update.message().messageId() + 1;
        buttons.createMenu(userId, messageId);
    }

    private String getUserQuestions (String userId) {
        String str = questions.getData(userId, 3) + "\n\n";
        return str;
    }

    private String getUserTables(String userId) {
        return buttons.getUserTables(userId);
    }

    public void callbackQuery(CallbackQuery callbackQuery) {
        // обработка кнопок
        String[] data = callbackQuery.data().split("/");
        String chatId = data[0];
        int messageId = Integer.parseInt(data[1]);
        String buttonId = data[2];

        String question = "";

        switch (buttonId) {
            case "2","27" -> {
                // досье спикеров
                buttons.createSpeakers(chatId,messageId);
                question = "Досье спикеров";
            }
            case "3", "28" -> {
                // задать вопрос из меню
                buttons.createQuest(chatId,messageId);
                question = "Задать вопрос из меню";
            }
            case "4" -> {
                // записаться на круглый стол
                constant.setDeleteTable("38");
                buttons.createTable(chatId,messageId);
                question = "Записаться на круглый стол";
            }
            case "38" -> {
                // удалиться с круглого стола
                constant.setDeleteTable("34");
                buttons.createDeleteTable(chatId,messageId);
                question = "Удалиться с круглого стола";
            }
            case "5", "6" -> {
                // выбрана секция
                buttons.createButtonsSpeakers(chatId,messageId,buttonId);
                question = "Выбрана секция";
                if (buttonId.equals("5")) {
                    question += "БЕЗОПАСНОСТЬ";
                } else {
                    question += "ТЕХНОЛОГИИ";
                }
            }

            case "7","29" -> {
                // задать вопрос на пленарном
                //constant.setSpeaker("26");
                setAction(chatId, constant.USER_FLAGS_QUESTION_PLENARY);
                buttons.createAskQuestion(chatId, messageId);
                question = "Задать вопрос на пленарном";
            }

            case "8" -> {
                // КС Цифровая Россия
                buttons.replyAnswerTable(chatId, messageId, constant.TABLE_NAME_1);
                question = "КС Цифровая Россия";
            }
            case "9" -> {
                // КС Цифровая Россия
                buttons.replyAnswerTable(chatId, messageId, constant.TABLE_NAME_2);
                question = "АйТи БАСТИОН";
            }
            case "10" -> {
                // КС СДИ Софт
                buttons.replyAnswerTable(chatId, messageId, constant.TABLE_NAME_3);
                question = "СДИ Софт";
            }
            case "11","12","13","14","15",
                 "16","17","18","19","20",
                    "21", "22", "23", "24", "25", "26" -> {
                // спикеры
                buttons.createSpeakerCard(chatId, messageId, buttonId);
                question = createSpeaker(buttonId);
            }
            case "30", "31", "32" -> {
                buttons.sendEditMenu(chatId, messageId);
                question = "В меню";
            }
            case "35" -> {
                // КС Цифровая Россия
                buttons.replyDeleteTable(chatId, messageId, constant.TABLE_NAME_1);
                question = "КС Цифровая Россия";
            }
            case "36" -> {
                // КС Цифровая Россия
                buttons.replyDeleteTable(chatId, messageId, constant.TABLE_NAME_2);
                question = "АйТи БАСТИОН";
            }
            case "37" -> {
                // КС СДИ Софт
                buttons.replyDeleteTable(chatId, messageId, constant.TABLE_NAME_3);
                question = "СДИ Софт";
            }
            case "111" -> {
                // все вопросы на пленарном
                replyPlenary(chatId, messageId);
                question = "Модератор - пленарное";
            }
            case "222" -> {
                // все вопросы ИБ
                buttons.replySafe(chatId, messageId);
                question = "Модератор - ИБ";
            }
            case "333" -> {
                // все вопросы ИТ
                buttons.replyTech(chatId, messageId);
                question = "Модератор - ИТ";
            }
            case "444" -> {
                // menu
                buttons.editMenuModerator(chatId, messageId);
                question = "Модератор - В меню";
            }
        }

        logging.saveLogButton(chatId,question);
    }

    private void replyPlenary (String userId, int messageId) {
        buttons.replyPlenary(userId, messageId);
    }

    public void sendMenuModerator(String userId, String messageId) {
        buttons.createMenuModerator(userId, messageId);
    }

    private String createSpeaker (String btnId) {
        /*  "11","12","13","14","15",
                 "16","17","18","19","20",
                    "21", "22", "23", "24", "25", "26"

         */

        switch (btnId) {
            case "11" -> {
                return "11";
            }
        }

        return "Name";
    }

//
//
//    public String getData(String userId, int i) {
//        return userDB.getData(userId, i);
//    }
//
//
//
//
//
//
//
//
//
//
//
//    public boolean notTable (String userId, String text) {
//        return userDB.isUserTable(userId, text);
//    }
//
//    private String getUserTables(String userId) {
//        String str = "";
//        if (userDB.isTable(userId)) {
//            // есть хотя бы одна запись на круглый стол
//            str += "\n\n\uD83D\uDD34 ВАШИ ЗАПИСИ:\n";
//            str += userDB.getTables(userId);
//        }
//        return str;
//    }
//
//    private String getUserQuestions (String userId) {
//        String str = "";
//        if (userDB.isTable(userId)) {
//            // есть хотя бы один вопрос
//            str += userDB.getQuestions(userId);
//        }
//        return str;
//    }
//
//    public void setUserTable (String userId, String text) {
//        userDB.setTable(userId,text);
//    }
//
//
//
//    private String getSpeakerCard(String idSection) {
//        String str = "";
//        for (int i = 0; i < speaker.getCount(idSection); i++) {
//            str += "▪\uFE0F " + speaker.getName(idSection,speaker.getId(idSection,i)) + "\n" +
//                    speaker.getDescription(idSection,speaker.getId(idSection,i)) + "\n\n";
//        }
//        return str;
//    }
//
//
//
//
//
//    private void askQuestion(String userId) {
//        setAction(userId,constant.USER_FLAGS_DEFAULT);
//
//        String question = update.message().text();
//        userDB.saveQuestion(userId,question,constant.PERSON);
//
//        sendQuestion(userId, question, constant.PERSON);
//
//        int id = update.message().messageId() + 2;
//        String messageId = Integer.toString(id);
//
//        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
//        inlineButtons[0] = new InlineKeyboardButton(btnName.MENU);
//        inlineButtons[0].callbackData(userId + "/" + messageId + "/" + constant.MENU);
//
//        String text = "Мы получили Ваш вопрос! Скоро на него ответят ";
//        text += "\n\n";
//        text += "❓ Ваш вопрос: " + question;
//        bot.execute(new SendMessage(userId,text)
//                .replyMarkup(
//                        new InlineKeyboardMarkup(
//                                inlineButtons
//                        )
//                ));
//    }
//
//    private void sendQuestion(String userId, String question, String speakerId) {
//
//        String text = "Вопрос: " + question;
//
//        bot.execute(new SendMessage(
//                userDB.getSpeakerId(speakerId),
//                text
//            ));
//
//    }
//
//    public boolean isSpeaker(String userId) {
//        return userDB.isSpeaker(userId);
//    }
//
//    public boolean isAdmin(String userId) {
//        return userDB.isAdmin(userId);
//    }
//
//    public void sendAdminMenu(String userId, int messageId) {
//        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
//        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Круглые столы");
//        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + "99");
//
//        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
//        inlineKeyboardButtons1[0] = new InlineKeyboardButton("Вопросы модератору");
//        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + "99");
//
//        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
//        inlineKeyboardButtons2[0] = new InlineKeyboardButton("Вопросы спикерам");
//        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + "99");
//
//        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
//        inlineKeyboardButtons3[0] = new InlineKeyboardButton("Просмотр всех");
//        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + "99");
//
//        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
//                inlineKeyboardButtons0,
//                inlineKeyboardButtons1,
//                inlineKeyboardButtons2,
//                inlineKeyboardButtons3
//        );
//
//
//        bot.execute(new SendMessage(userId, "Главное меню:")
//                .replyMarkup(inlineKeyboard));
//
//    }
}
