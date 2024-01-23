package bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetWebhook;
import constant.Constant;

import java.io.IOException;


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
                        bot.execute(new SendMessage(userId, "j"));

                        bot.execute(new SendMessage(userId, "https://telegra.ph/Mezhregionalnyj-cifrovoj-forum-Obmen-opytom-vnedreniya-i-ehkspluatacii-Rossijskih-produktov-01-23"));

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
                    case "1" -> {
                        // программа мероприятия
                        bot.execute(new SendMessage(chatId,"t"));
                    }
                    case "2" -> {
                        // досье спикеров
                    }
                    case "3" -> {
                        // задать вопрос из меню
                    }
                    case "4" -> {
                        // записаться на круглый стол
                    }
                }

            }
        }

        if (request != null) {
            bot.execute(request);
        }

    }

}
