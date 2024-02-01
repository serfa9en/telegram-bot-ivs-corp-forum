package bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
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
                //test();

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
                        // bot.execute(new SendMessage(userId, "Вы модератор!"));
                        switch (text) {
                            case "/start":
                            case "/start@itforum_2024_bot":
                            case "/show_info":
                            case "/show_info@itforum_2024_bot": {
                                bot.execute(new SendMessage(userId, "Вы модератор!"));
                                break;
                            }

                            case "/menu":
                            case "/menu@itforum_2024_bot": {
                                int messageId = update.message().messageId() + 1;
                                action.sendMenuModerator(userId.toString(), Integer.toString(messageId));
                                break;
                            }

                            default: {
                                action.doDefault(userId.toString());
                            }
                        }
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
                                        action.sendCardUser(userId.toString());
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

    private void test() {
        String[] temp = {"1", "2", "3", "2", "2", "5", "2", "3", "4", "5", "5", "4"};
        String[] arr = new String[0];
        String[] temp2 = temp;
        String el = "";


        for (int i = 0; i < temp.length; i++) {
            boolean flag = true;
            while (flag) {
                el = temp[i];
                for (int j = i+1; j < temp.length; j++) {
                    if (el.equals(temp[j])) {
                        flag = true;
                    }
                }
                if (flag) {
                    temp = update(temp, i);
                } else {
                    String[] arr2 = arr;
                    arr = new String[arr.length+1];
                    for (int j = 0; j < arr2.length; j++) {
                        arr[j] = arr2[j];
                    }
                    arr[arr2.length] = el;
                    flag = false;
                }
            }
        }

    }

    private void print (String[] temp) {
        for(int i = 0; i < temp.length; i++) {
            System.out.print(temp[i] + "  ");
        }
        System.out.println();
    }

    private String[] update (String[] temp, int j) {
        String[] str = new String[temp.length-1];
        int index = 0;
        for (int i = 1; i < temp.length; i++) {
            
        }
        return str;
    }

}
