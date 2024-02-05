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
import properties.Logging;


public class Bot {

    private final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));
    private final String userFile = "/C:/Users/mbelyaeva/IdeaProjects/telegram-bot-ivs-corp-forum-version2/telegram-bot-ivs-corp-forum-version2/src/main/java/files/dataBased.xlsx";
    private Action action;
    private Constant constant;
    private Logging logging;

    public void serve() {
        action = new Action(bot);
        action.createDataBase(userFile);
        constant = new Constant();
        logging = new Logging(userFile);
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
                //bot.execute(new SendMessage("5550842004",logging.createLogText(userId.toString(), text)));
                logging.saveLogText(userId.toString(), text);
                System.out.print(logging.createLogText(userId.toString(), text));
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
                        switch (text) {
                            case "/start":
                            case "/start@itforum_2024_bot":
                            case "/menu":
                            case "/menu@itforum_2024_bot": {

                            }
                        }

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
                                action.setActionModerator(userId.toString(), constant.MODERATOR_FLAG_DEFAULT);
                                bot.execute(new SendMessage(userId, "Вы модератор!"));
                                break;
                            }

                            case "/menu":
                            case "/menu@itforum_2024_bot": {
                                action.setActionModerator(userId.toString(), constant.MODERATOR_FLAG_DEFAULT);
                                int messageId = update.message().messageId() + 1;
                                action.sendMenuModerator(userId.toString(), Integer.toString(messageId));
                                break;
                            }

                            default: {
                                action.doDefaultModerator(userId.toString());
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
            } else {
                // отправили не текст
                if (action.isPerson(message.chat().id().toString()) == 2) {
                    action.doDefaultModerator(message.chat().id().toString());
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
        String[] arr = new String[1];
        arr[0] = temp[0];

        String el = "";

        for (int i = 1; i < temp.length; i++) {
            el = temp[i];
            boolean flag = false;
            for (int j = 0; j < arr.length; j++) {
                if (el.equals(arr[j])) {
                    flag = true;
                }
            }
            //System.out.println();
            if (!flag) {
                String[] t = arr;
                arr = new String[t.length+1];
                for (int k = 0; k < t.length; k++) {
                    arr[k] = t[k];
                }
                arr[t.length] = el;
            }
        }
        print(arr);

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
