package bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetWebhook;
import constant.Constant;

import java.io.IOException;


public class Bot {

    private final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));
    private final String userFile = "/home/masha/Рабочий стол/telegram bot/telegram-bot-ivs-corp-forum-master/src/main/java/based/users.xlsx";
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
                                request = (new SendMessage(chatId, "Пожалуйста, введите ФИО (как к Вам обращаться?)\n"));
                                action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                            } else {
                                request = new SendMessage(chatId, action.getCardUser(userId.toString()));
                            }
                        } else {
                            request = (new SendMessage(chatId, "Пожалуйста, введите ФИО (как к Вам обращаться?)\n"));
                            action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                            action.createNewUser(userId.toString());
                        }
                        break;

                    case "/menu":
                    case "/menu@itforum_2024_bot": {
                        action.setAction(userId.toString(), constant.USER_FLAGS_DEFAULT);
                        if (action.check(userId.toString())) {
                            if (action.getData(userId.toString(), 4).equals(" ")) {
                                request = (new SendMessage(chatId, "Пожалуйста, введите ФИО (как к Вам обращаться?)\n"));
                                action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                            } else {
                                int messageId = message.messageId() + 1;
                                action.sendMenu(userId.toString(), messageId);
                            }
                        } else {
                            request = (new SendMessage(chatId, "Пожалуйста, введите ФИО (как к Вам обращаться?)\n"));
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

                switch (buttonId) {
                    case "2" -> {
                        // досье спикеров
                        action.createSpeakers(chatId,messageId);
                    }
                    case "3" -> {
                        // задать вопрос из меню
                        action.createQuest(chatId,messageId);
                    }
                    case "4" -> {
                        // записаться на круглый стол
                        action.createTable(chatId,messageId);
                    }
                    case "5" -> {
                        // выбрана секция инф безопасность
                        action.createSecuritySpeakers(chatId,messageId);
                    }
                    case "6" -> {
                        // выбрана секция инф технологии
                        action.createTecnologySpeakers(chatId,messageId);
                    }
                    case "7" -> {
                        // выбрана секция инф безопасность вопрос
                        action.createSecuritySpeakers(chatId,messageId);
                    }
                    case "8" -> {
                        // выбрана секция инф технологии вопрос
                        action.createTecnologySpeakers(chatId,messageId);
                    }
                    case "9" -> {
                        // выбрана секция пленарное заседание
                    }
                    case "10" -> {
                        // КС Цифровая Россия
                        String tableName = "13:00-15:00 — Цифровая Россия (Зал Edison)";
                        replyAnswerTable(chatId, messageId, tableName);

                    }
                    case "11" -> {
                        // КС АйТи БАСТИОН
                        String tableName = "15:30-18:45 — АйТи БАСТИОН (Зал Edison)";
                        replyAnswerTable(chatId, messageId, tableName);
                    }
                    case "12" -> {
                        // КС СДИ Софт
                        String tableName = "16:45-17:30 — СДИ Софт (Информационная безопасность)";
                        replyAnswerTable(chatId, messageId, tableName);
                    }
                }

            }
        }

        if (request != null) {
            bot.execute(request);
        }

    }

    private void replyAnswerTable (String chatId, int messageId, String tableName) {
        if (action.notTable(chatId, tableName)) {
            action.setUserTable(chatId, tableName);
            bot.execute(new EditMessageText(chatId, messageId, "Вы записаны"));
        } else {
            bot.execute(new EditMessageText(chatId, messageId, "Вы записаны"));
        }
    }

}
