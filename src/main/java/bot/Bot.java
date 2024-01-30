package bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.*;
import properties.Constant;


public class Bot {

    private final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));
    private final String userFile = "/home/ICS_HOME/mbelyaeva/Рабочий стол/Работа/TG_bot/Форум/ItForum/src/main/java/files/dataBased.xlsx";
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

                // action.isPerson(userId)
                // 1 - Admin
                // 2 - Moderator
                // 3 - Speaker
                // 4 - User

                switch (action.isPerson(userId.toString())) {
                    case 1 -> {
                        // admin
                        bot.execute(new SendMessage(userId, "Вы админ!"));
//                        switch (text) {
//                            case "/show_info":
//                            case "/show_info@itforum_2024_bot": {
//                                bot.execute(new SendMessage(
//                                        userId, "Вы админ!"
//                                ));
//                                break;
//                            }
//
//                            case "/start":
//                            case "/start@itforum_2024_bot":
//                            case "/menu":
//                            case "/menu@itforum_2024_bot": {
//                                int messageId = message.messageId() + 1;
//                                action.sendAdminMenu(userId.toString(), messageId);
//                                break;
//                            }
//
//                            default: {
//                                action.doDefault(userId.toString());
//                            }
//                        }
                    }
                    case 2 -> {
                        // moderator
                        bot.execute(new SendMessage(userId, "Вы модератор!"));
                    }
                    case 3 -> {
                        // speaker
                        bot.execute(new SendMessage(userId, "Вы speaker!"));
                    }
                    case 4 -> {
                        // user
                        // bot.execute(new SendMessage(userId, "Вы пользователь!"));

                        switch (text) {
                            case "/start":
                            case "/start@itforum_2024_bot":
                            case "/show_info":
                            case "/show_info@itforum_2024_bot": {
                                if (action.checkIs(userId.toString())) {
                                    action.setAction(userId.toString(), constant.USER_FLAGS_DEFAULT);
                                    if (action.checkName(userId.toString())) {
                                        request = new SendMessage(chatId, action.getCardUser(userId.toString()));
                                    } else {
                                        request = (new SendMessage(chatId, constant.REGISTRY));
                                        action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                                    }
                                } else {
                                    action.createNewUser(userId.toString());
                                    request = (new SendMessage(chatId, constant.REGISTRY));
                                    action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                                }
                                break;
                            }

                            case "/menu":
                            case "/menu@itforum_2024_bot": {
                                if (action.checkIs(userId.toString())) {
                                    action.setAction(userId.toString(), constant.USER_FLAGS_DEFAULT);
                                    if (action.checkName(userId.toString())) {
                                        action.sendMenu(userId.toString());
                                    } else {
                                        request = (new SendMessage(chatId, constant.REGISTRY));
                                        action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                                    }
                                } else {
                                    action.createNewUser(userId.toString());
                                    request = (new SendMessage(chatId, constant.REGISTRY));
                                    action.setAction(userId.toString(), constant.USER_FLAGS_REGISTRY);
                                }
                                break;
                            }

                            default: {
                                action.doDefault(userId.toString());
                            }
                        }
                    }
                    default ->
                        System.out.println("default");

                }
            }

        } else {
            if (callbackQuery != null) {
                action.callbackQuery(callbackQuery);
            }
        }

        if (request != null) {
            bot.execute(request);
        }

    }

//    private void replyAnswerTable (String chatId, int messageId, String tableName) {
//        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
//        inlineButtons[0] = new InlineKeyboardButton("\uD83D\uDD1A В меню");
//        inlineButtons[0].callbackData(chatId + "/" + messageId + "/" + constant.MENU);
//
//        if (action.notTable(chatId, tableName)) {
//            action.setUserTable(chatId, tableName);
//            bot.execute(new EditMessageText(chatId, messageId, "Вы записаны")
//                    .replyMarkup(
//                            new InlineKeyboardMarkup(
//                                    inlineButtons
//                            )
//                    ));
//        } else {
//            bot.execute(new EditMessageText(chatId, messageId, "Вы записаны")
//                    .replyMarkup(
//                            new InlineKeyboardMarkup(
//                                    inlineButtons
//                            )
//                    ));
//        }
//    }

}
