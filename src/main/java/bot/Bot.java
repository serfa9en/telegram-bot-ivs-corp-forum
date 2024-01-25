package bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.*;
import properties.Constant;


public class Bot {

    private final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));
    private final String userFile = "/home/ICS_HOME/mbelyaeva/Рабочий стол/Работа/TG_bot/Форум/ItForum/src/main/java/based/users.xlsx";
    private Action action;
    private Constant constant;

    public void serve() {
        action = new Action(bot);
        action.createDataBase(userFile);
        constant = new Constant();
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void process(Update update) {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();
        action.setUpdate(update);


        String text;
        BaseRequest request = null;

        if (message != null) {
            Long chatId = message.chat().id();
            Long userId;
            text = message.text();

            // System.out.println(update.message().photo()[0].fileId());

            // текст
            if (text != null) {
                userId = message.chat().id();

                switch (text) {
                    case "/start":
                    case "/start@itforum_2024_bot":
                    case "/show_info":
                    case "/show_info@itforum_2024_bot":
                        action.setAction(userId.toString(), constant.USER_FLAGS_DEFAULT);
                        if (action.check(userId.toString())) {
                            if (action.getData(userId.toString(), 4).equals(" ")) {
                                request = (new SendMessage(chatId, "❗\uFE0F Пожалуйста, зарегистрируйтесь:\n\nВведите ФИО (как к Вам обращаться)\nНазвание компании\n"));
                                action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                            } else {
                                request = new SendMessage(chatId, action.getCardUser(userId.toString()));
                            }
                        } else {
                            request = (new SendMessage(chatId, "❗\uFE0F Пожалуйста, зарегистрируйтесь:\n\nВведите ФИО (как к Вам обращаться)\nНазвание компании\n"));
                            action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                            action.createNewUser(userId.toString());
                        }
                        break;

                    case "/menu":
                    case "/menu@itforum_2024_bot": {
                        action.setAction(userId.toString(), constant.USER_FLAGS_DEFAULT);
                        if (action.check(userId.toString())) {
                            if (action.getData(userId.toString(), 4).equals(" ")) {
                                request = (new SendMessage(chatId, "❗\uFE0F Пожалуйста, зарегистрируйтесь:\n\nВведите ФИО (как к Вам обращаться)\nНазвание компании\n"));
                                action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                            } else {
                                int messageId = message.messageId() + 1;
                                action.sendMenu(userId.toString(), messageId);
                            }
                        } else {
                            request = (new SendMessage(chatId, "❗\uFE0F Пожалуйста, зарегистрируйтесь:\n\nВведите ФИО (как к Вам обращаться)\nНазвание компании\n"));
                            action.createNewUser(userId.toString());
                            action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                        }
                        break;
                    }

                    default: {
                        action.doDefault(userId.toString());
                    }
                }
            }

        } else {
            if (callbackQuery != null) {

                // обработка кнопок
                String[] data = callbackQuery.data().split("/");
                String chatId = data[0];
                int messageId = Integer.parseInt(data[1]);
                String buttonId = data[2];
                // System.out.println(buttonId);

                switch (buttonId) {
                    case "2","23" -> {
                        // досье спикеров
                        //constant.setId("23");
                        action.createSpeakers(chatId,messageId);
                    }
                    case "3", "24" -> {
                        // задать вопрос из меню
                        //constant.setId("24");
                        action.createQuest(chatId,messageId);
                        // bot.execute(new SendDocument(chatId,"/home/ICS_HOME/mbelyaeva/Рабочий стол/Работа/TG_bot/Форум/ItForum/src/main/resources/form.html"));
                    }
                    case "4" -> {
                        // записаться на круглый стол
                        action.createTable(chatId,messageId);
                    }
                    case "5" -> {
                        // выбрана секция инф безопасность
                        action.createButtonsSpeakers(chatId,messageId,"5");
                    }
                    case "6" -> {
                        // выбрана секция инф технологии
                        action.createButtonsSpeakers(chatId,messageId,"6");
                    }

                    case "8" -> {
                        // КС Цифровая Россия
                        String tableName = "13:00-15:00 — Цифровая Россия (Зал Edison)";
                        replyAnswerTable(chatId, messageId, tableName);

                    }
                    case "9" -> {
                        // КС Цифровая Россия
                        String tableName = "15:30 - 18:45 — АйТи БАСТИОН";
                        replyAnswerTable(chatId, messageId, tableName);

                    }
                    case "20" -> {
                        // КС СДИ Софт
                        String tableName = "16:45 – 18:15 — СДИ Софт (Информация безопасность)";
                        replyAnswerTable(chatId, messageId, tableName);
                    }
                    case "10","11","12","13","14",
                            "15", "16", "17", "18", "19" -> {
                        action.createSpeakerCard(chatId, messageId, buttonId);
                    }
                    case "21", "22" -> {
                        action.sendEditMenu(chatId,messageId);
                    }
                }

            }
        }

        if (request != null) {
            bot.execute(request);
        }

    }

    private void replyAnswerTable (String chatId, int messageId, String tableName) {
        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
        inlineButtons[0] = new InlineKeyboardButton("\uD83D\uDD1A В меню");
        inlineButtons[0].callbackData(chatId + "/" + messageId + "/" + constant.MENU);

        if (action.notTable(chatId, tableName)) {
            action.setUserTable(chatId, tableName);
            bot.execute(new EditMessageText(chatId, messageId, "Вы записаны")
                    .replyMarkup(
                            new InlineKeyboardMarkup(
                                    inlineButtons
                            )
                    ));
        } else {
            bot.execute(new EditMessageText(chatId, messageId, "Вы записаны")
                    .replyMarkup(
                            new InlineKeyboardMarkup(
                                    inlineButtons
                            )
                    ));
        }
    }

}
