package properties;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import dataBased.*;


public class Buttons {
    private TelegramBot bot;
    private Update update;

    Constant constant = new Constant();
    DataBased speaker;
    DataBased table;
    DataBased question;
    DataBased moderator;

    public Buttons (TelegramBot tbot) {
        this.bot = tbot;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public void setFile (String file) {
        speaker = new Speaker(file);
        table = new Table(file);
        question = new Question(file);
        moderator = new Moderator(file);
    }

    private final String MENU_PROGRAMM = "Программа мероприятия";
    private final String MENU_SPEAKERS = "Досье спикеров";
    private final String MENU_QUESTION = "Задать вопрос";
    private final String MENU_TABLE = "Записаться на круглый стол";

    private final String[] SPEAKERS_SECTIONS = {
            "Информационная безопасность",
            "Информационные технологии"
    };

    private final String QUEST_PLENARY = "Пленарное заседание";

    private final String BACK = "\uD83D\uDD19 Назад";
    private final String MENU = "\uD83D\uDD1A В меню";
    private final String CANCEL_QUEST = "⛔\uFE0F Отмена";


    public void createMenu(String userId, int messageId) {
        System.out.println("buttons menu");
        bot.execute(new SendMessage(userId, "Главное меню:")
                .replyMarkup(createMenuButtons(userId, messageId)));
    }

    public void sendEditMenu(String userId, int messageId) {
        bot.execute(new EditMessageText(userId,messageId, "Главное меню:")
                .replyMarkup(createMenuButtons(userId, messageId)));

    }

    private InlineKeyboardMarkup createMenuButtons (String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton(MENU_PROGRAMM);
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_PROGRAM);
        inlineKeyboardButtons0[0].url("https://telegra.ph/Mezhregionalnyj-cifrovoj-forum-Obmen-opytom-vnedreniya-i-ehkspluatacii-Rossijskih-produktov-01-23");

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton(MENU_SPEAKERS);
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_SPEAKERS);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton(MENU_QUESTION);
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_QUEST);

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton(MENU_TABLE);
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_TABLE);

        return new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3
        );
    }

    // досье спикеров
    public void createSpeakers (String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton(SPEAKERS_SECTIONS[0]);
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKERS_SECURITY);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton(SPEAKERS_SECTIONS[1]);
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKERS_TECNOLOGY);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton(BACK);
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.BACK);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2
        );

        constant.setIdBack("27");
        String text = "<b>" + MENU_SPEAKERS.toUpperCase() + "</b>\n\n" + "Выберите секцию:";

        bot.execute(new EditMessageText (userId, messageId, text)
                .parseMode(ParseMode.HTML)
                .replyMarkup(inlineKeyboard));
    }

    //задать вопрос
    public void createQuest (String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton(SPEAKERS_SECTIONS[0]);
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKERS_SECURITY);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton(SPEAKERS_SECTIONS[1]);
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKERS_TECNOLOGY);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton(QUEST_PLENARY);
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.QUEST_PLENARY);

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton(BACK);
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.BACK);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3
        );

        constant.setIdBack("28");
        constant.setType("1"); //пленарка
        constant.setSpeaker("33");

        String text = "<b>" + MENU_QUESTION.toUpperCase() + "</b>\n\n" + "Выберите секцию:";
        bot.execute(new EditMessageText (userId, messageId, text)
                .parseMode(ParseMode.HTML)
                .replyMarkup(inlineKeyboard));
    }

    // круглый стол
    public void createTable (String userId, int messageId) {
        //System.out.println(constant.DELETE_TABLE);
        constant.setDeleteTable1("8");
        constant.setDeleteTable2("9");
        constant.setDeleteTable3("10");

        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Цифровая Россия");
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.TABLE_1);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton("АйТи БАСТИОН");
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.TABLE_2);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton("СДИ Софт");
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.TABLE_3);

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton(BACK);
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.BACK);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3
        );

        bot.execute(new EditMessageText (userId, messageId,
                "\uD83D\uDCCD <b>ЗАПИСАТЬСЯ НА КРУГЛЫЙ СТОЛ</b>\n" +
                        constant.TEXT_TABLE
                + getUserTables(userId)).parseMode(ParseMode.HTML)
                .replyMarkup(inlineKeyboard));
    }

    public void createDeleteTable (String userId, int messageId) {
        constant.setDeleteTable1("35");
        constant.setDeleteTable2("36");
        constant.setDeleteTable3("37");

        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Цифровая Россия");
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.TABLE_1);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton("АйТи БАСТИОН");
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.TABLE_2);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton("СДИ Софт");
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.TABLE_3);

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton(BACK);
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.BACK);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3
        );

        bot.execute(new EditMessageText (userId, messageId,
                "\uD83D\uDCCD <b>УДАЛИТЬ ЗАПИСЬ НА КРУГЛЫЙ СТОЛ</b>\n" +
                        constant.TEXT_TABLE
                + getUserTables(userId)).parseMode(ParseMode.HTML)
                .replyMarkup(inlineKeyboard));
    }

    public String getUserTables (String userId) {
        String str = "";
        if (table.isPerson(userId)) {
            // есть хотя бы одна запись на круглый стол
            str += "\n\uD83D\uDD34 <b>ВЫ ЗАПИСАНЫ НА КРУГЛЫЙ СТОЛ:</b>\n";
            str += table.getData(userId, 1);
        }
        return str;
    }

    public void replyAnswerTable (String chatId, int messageId, String tableName) {
        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
        inlineButtons[0] = new InlineKeyboardButton(MENU);
        inlineButtons[0].callbackData(chatId + "/" + messageId + "/" + constant.MENU);

        if(isTable(chatId,tableName)) {
            // если такой стол у такого пользователя уже есть
            if (isTrue(chatId, tableName)) {
                // если пользователь записан на этот стол (флаг 1)

            } else {
                // если пользователь не записан на этот стол (флаг 0)
                String flag = tableName + "/" + "1";
                table.setFlag(chatId, flag);
            }
        } else {
            // если такого стола у такого пользователя ещё нет
            table.setData(chatId, tableName, 1);
        }

        /*
        if (trueTable(chatId, tableName).equals("0")) {
            table.setData(chatId, tableName, 1);
        }
        if (trueTable(chatId, tableName).equals("1")) {
            String flag = tableName + "/" + "1";
            table.setFlag(chatId, flag);
        }

         */

        bot.execute(new EditMessageText(chatId, messageId, "Вы записаны")
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                inlineButtons
                        )
                ));
    }

    public void replyDeleteTable (String chatId, int messageId, String tableName) {
        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
        inlineButtons[0] = new InlineKeyboardButton(MENU);
        inlineButtons[0].callbackData(chatId + "/" + messageId + "/" + constant.MENU);

        if(isTable(chatId,tableName)) {
            // если такой стол у такого пользователя уже есть
            if (isTrue(chatId, tableName)) {
                // если пользователь записан на этот стол (флаг 1)
                String flag = tableName + "/" + "0";
                table.setFlag(chatId, flag);
            } else {
                // если пользователь не записан на этот стол (флаг 0)

            }
        } else {
            // если такого стола у такого пользователя ещё нет
            table.setData(chatId, tableName, 0);
        }

        /*
        if (trueTable(chatId, tableName).equals("0")) {
            String flag = tableName + "/" + "0";
            table.setFlag(chatId, flag);
            // table.setData(chatId, tableName, 1);
        }

         */

        bot.execute(new EditMessageText(chatId, messageId, "Вы удалили запись")
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                inlineButtons
                        )
                ));
    }

    public boolean isTable (String userId, String text) {
        String[] tbTemp = table.getDataArray(userId, 10);

        boolean flag = false;
        for(int i = 0; i < tbTemp.length; i++) {
            if (tbTemp[i].equals(text)) {
                flag = true;
            }
        }

        /*
        for (int i = 0; i < tbTemp.length; i++) {
            System.out.println(tbTemp[i]);
        }
        System.out.println();

         */

        return flag;
    }

    public boolean isTrue (String userId, String text) {
        String[] tbTemp = table.getDataArray(userId, 1);

        boolean flag = false;
        for(int i = 0; i < tbTemp.length; i++) {
            if (tbTemp[i].equals(text)) {
                flag = true;
            }
        }

        /*
        for (int i = 0; i < tbTemp.length; i++) {
            System.out.println(tbTemp[i]);
        }
        System.out.println();

         */

        return flag;
    }

    public String trueTable (String userId, String text) {
        String[] tbTemp = table.getDataArray(userId, 1);
        String[] tbTemp2 = table.getDataArray(userId, 0);

        String flag = "";
        for(int i = 0; i < tbTemp.length; i++) {
            if (tbTemp[i].equals(text)) {
                flag = "0";
            }
        }

        for(int i = 0; i < tbTemp2.length; i++) {
            if (tbTemp2[i].equals(text)) {
                flag = "1";
            }
        }

        return flag;
    }


    public void createButtonsSpeakers (String userId, int messageId, String idSection) {
        String[][] sp = new String[speaker.getCount(idSection)][5];
        for (int i = 0; i < sp.length; i++) {
            sp[i] = speaker.getDataArray(idSection, (i + 1));
        }

        InlineKeyboardButton[][] inlineButtons = new InlineKeyboardButton[sp.length+2][1];
        for(int i = 0; i < inlineButtons.length-2; i++) {
            inlineButtons[i][0] = new InlineKeyboardButton(sp[i][2]);
            inlineButtons[i][0].callbackData(userId + "/" + messageId + "/" + sp[i][0]);
        }

        String text = "";
        if (idSection.equals("5")) {
            text =  "<b>" + SPEAKERS_SECTIONS[0].toUpperCase() + "</b>";
        } else {
            text =  "<b>" + SPEAKERS_SECTIONS[1].toUpperCase() + "</b>";
        }

        inlineButtons[inlineButtons.length-2][0] = new InlineKeyboardButton(BACK);
        inlineButtons[inlineButtons.length-2][0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_BACK);
        inlineButtons[inlineButtons.length-1][0] = new InlineKeyboardButton(MENU);
        inlineButtons[inlineButtons.length-1][0].callbackData(userId + "/" + messageId + "/" + constant.MENU);

        bot.execute(new EditMessageText (userId, messageId, text + ":\n\n" + getSpeakerCard(sp))
                .parseMode(ParseMode.HTML)
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                inlineButtons
                        )
                ));
    }

    private String getSpeakerCard(String[][] sp) {
        String str = "";
        for (int i = 0; i < sp.length; i++) {
            str += "\uD83D\uDD3B <b>" + sp[i][2] + "</b>\n" +
                    sp[i][3] + "\n\n➖" + sp[i][4] + "\n\n\n";
        }
        return str;
    }

    public void createAskQuestion(String userId, int messageId) {
        String text = "";
        if (constant.QUESTION_TYPE.equals("1")) {
            text = "Данный вопрос будет рассматриваться на пленарном заседании";
        } else {
            text = "\uD83D\uDD3B Спикер: <b>" + speaker.getData(constant.PERSON, 8) + "</b>\n"
                    + speaker.getData(constant.PERSON, 9);
        }

        text += "\n\n";
        text += "❗\uFE0F Отправьте Ваш вопрос:";

        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
        inlineButtons[0] = new InlineKeyboardButton(CANCEL_QUEST);
        inlineButtons[0].callbackData(userId + "/" + messageId + "/" + constant.CANCEL_QUEST);

        bot.execute(new EditMessageText(userId, messageId, text)
                .parseMode(ParseMode.HTML)
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                inlineButtons
                        )
                ));
    }

    public void askQuestion(String userId) {
        String questionText = update.message().text();
        String data = userId + "/" + constant.PERSON;
        question.setData(data,questionText,Integer.parseInt(constant.QUESTION_TYPE));

        // System.out.println(constant.PERSON);
        sendQuestion(questionText);

        // кол-во модераторов
        int count = moderator.getCount("");
        if (!constant.PERSON.equals("33")) count++;

        int id = update.message().messageId() + count;
        String messageId = Integer.toString(id);

        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
        inlineButtons[0] = new InlineKeyboardButton(MENU);
        inlineButtons[0].callbackData(userId + "/" + messageId + "/" + constant.MENU);

        String text = "Мы получили Ваш вопрос! Скоро на него ответят ";
        text += "\n\n";
        text += "❓ Ваш вопрос: " + questionText;
        bot.execute(new SendMessage(userId,text)
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                inlineButtons
                        )
                ));
    }

    private void sendQuestion(String question) {

        int count = moderator.getCount("") - 1;
        String[] toSmt = moderator.getDataArray("", count);

        String text = "";

        if (constant.PERSON.equals("33")) {
            // пленарка
            text = "#пленарное_заседание -> ";
        } else {
            // спикер
            if (speaker.getData(constant.PERSON,5).equals("5")) {
                text += "#информационная_безопасность";
            } else {
                text += "#информационные_технологии";
            }

            text += " <b>" + speaker.getData(constant.PERSON,8) + " ("
                + speaker.getData(constant.PERSON,7) + ")</b> -> ";

            toSmt = addElem(toSmt, speaker.getData(constant.PERSON,0));

        }

        text += question;

        for (int i = 0; i < toSmt.length; i++) {
            bot.execute(new SendMessage(toSmt[i], text)
                    .parseMode(ParseMode.HTML));
        }

    }

    private String[] addElem (String[] array, String elem) {
        String[] temp = new String[array.length + 1];
        for(int i = 0; i < array.length; i++) {
            temp[i] = array[i];
        }
        temp[array.length] = elem;
        return temp;
    }

    public void createSpeakerCard (String userId, int messageId, String idSpeaker) {
        // задать вопрос спикеру
        constant.setType("0");
        // System.out.println(idSpeaker);

        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Задать вопрос");
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.QUEST_SPEAKER);

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton(BACK);
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_BACK);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton(MENU);
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.MENU);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2
        );

        String str = "\uD83D\uDD3B <b>" + speaker.getData(idSpeaker, 8) + "</b>\n"
                + speaker.getData(idSpeaker, 9) + "\n\n➖"
                + speaker.getData(idSpeaker, 10);

        constant.setSpeaker(idSpeaker);

        bot.execute(new EditMessageText(userId, messageId, str)
                .parseMode(ParseMode.HTML)
                .replyMarkup(inlineKeyboard));
    }

    public InlineKeyboardMarkup getButtonsCardUser (String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons = new InlineKeyboardButton[1];
        inlineKeyboardButtons[0] = new InlineKeyboardButton("Удалить запись на круглый стол");
        inlineKeyboardButtons[0].callbackData(userId + "/" + messageId + "/" + constant.DELETE_TABLE_MENU);

        //constant.setDeleteTable("34");

        return new InlineKeyboardMarkup(inlineKeyboardButtons);
    }

    public void createMenuModerator(String userId, String messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton(MENU_PROGRAMM);
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_PROGRAM);
        inlineKeyboardButtons0[0].url("https://telegra.ph/Mezhregionalnyj-cifrovoj-forum-Obmen-opytom-vnedreniya-i-ehkspluatacii-Rossijskih-produktov-01-23");

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton(QUEST_PLENARY);
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_MODERATOR_PLENARY);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton(SPEAKERS_SECTIONS[0]);
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_MODERATOR_SAFE);

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton(SPEAKERS_SECTIONS[1]);
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_MODERATOR_TECH);

        InlineKeyboardButton[] inlineKeyboardButtons4 = new InlineKeyboardButton[1];
        inlineKeyboardButtons4[0] = new InlineKeyboardButton("Рассылка");
        inlineKeyboardButtons4[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_SEND_SEND);


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3,
                inlineKeyboardButtons4
        );

        bot.execute(new SendMessage(userId, "Главное меню:")
                .replyMarkup(inlineKeyboardMarkup));
    }

    public void editMenuModerator(String userId, int messageId) {
        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
        inlineKeyboardButtons0[0] = new InlineKeyboardButton(MENU_PROGRAMM);
        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_PROGRAM);
        inlineKeyboardButtons0[0].url("https://telegra.ph/Mezhregionalnyj-cifrovoj-forum-Obmen-opytom-vnedreniya-i-ehkspluatacii-Rossijskih-produktov-01-23");

        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
        inlineKeyboardButtons1[0] = new InlineKeyboardButton(QUEST_PLENARY);
        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_MODERATOR_PLENARY);

        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
        inlineKeyboardButtons2[0] = new InlineKeyboardButton(SPEAKERS_SECTIONS[0]);
        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_MODERATOR_SAFE);

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton(SPEAKERS_SECTIONS[1]);
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_MODERATOR_TECH);

        InlineKeyboardButton[] inlineKeyboardButtons4 = new InlineKeyboardButton[1];
        inlineKeyboardButtons4[0] = new InlineKeyboardButton("Рассылка");
        inlineKeyboardButtons4[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_SEND_SEND);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3,
                inlineKeyboardButtons4
        );

        bot.execute(new EditMessageText(userId, messageId, "Главное меню:")
                .replyMarkup(inlineKeyboardMarkup));
    }

    public void replyPlenary (String userId, int messageId) {
        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
        inlineButtons[0] = new InlineKeyboardButton(MENU);
        inlineButtons[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_MODERATOR);

        String str = "\uD83D\uDCCC <b>ПЛЕНАРНОЕ ЗАСЕДАНИЕ</b>\n\n";
        String[] array = question.getDataArray("",1);

        for (int i = 0; i < array.length; i++) {
            str += "▪\uFE0F " + array[i] + "\n\n";
        }

        bot.execute(new EditMessageText(userId, messageId, str)
                .parseMode(ParseMode.HTML)
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                inlineButtons
                        )
                ));
    }

    public void replySafe (String userId, int messageId) {
        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
        inlineButtons[0] = new InlineKeyboardButton(MENU);
        inlineButtons[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_MODERATOR);

        String str = "\uD83D\uDCCC <b>ИНФОРМАЦИОННАЯ БЕЗОПАСНОСТЬ</b>\n\n";
        String[] array = question.getDataArray("",5);

        for (int i = 0; i < array.length; i++) {
            str += "▪\uFE0F " + array[i] + "\n\n";
        }

        bot.execute(new EditMessageText(userId, messageId, str)
                .parseMode(ParseMode.HTML)
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                inlineButtons
                        )
                ));
    }

    public void replyTech (String userId, int messageId) {
        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
        inlineButtons[0] = new InlineKeyboardButton(MENU);
        inlineButtons[0].callbackData(userId + "/" + messageId + "/" + constant.MENU_MODERATOR);

        String str = "\uD83D\uDCCC <b>ИНФОРМАЦИОННЫЕ ТЕХНОЛОГИИ</b>\n\n";
        String[] array = question.getDataArray("",6);

        for (int i = 0; i < array.length; i++) {
            str += "▪\uFE0F " + array[i] + "\n\n";
        }

        bot.execute(new EditMessageText(userId, messageId, str)
                .parseMode(ParseMode.HTML)
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                inlineButtons
                        )
                ));
    }

    public void sendSend (String userId, int messageId) {
        InlineKeyboardButton[] inlineButtons0 = new InlineKeyboardButton[1];
        inlineButtons0[0] = new InlineKeyboardButton("Только текст");
        inlineButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.MODERATOR_SENT_TEXT);

        InlineKeyboardButton[] inlineButtons1 = new InlineKeyboardButton[1];
        inlineButtons1[0] = new InlineKeyboardButton("Картинка + текст");
        inlineButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.MODERATOR_SENT_PIC_TEXT);

        InlineKeyboardButton[] inlineButtons2 = new InlineKeyboardButton[1];
        inlineButtons2[0] = new InlineKeyboardButton("Картинка");
        inlineButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.MODERATOR_SENT_PIC);

        String str = "\uD83D\uDCCC <b>ВЫБЕРИТЕ ТИП СООБЩЕНИЯ</b>\n\n";

        bot.execute(new EditMessageText(userId, messageId, str)
                .parseMode(ParseMode.HTML)
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                inlineButtons0,
                                inlineButtons1,
                                inlineButtons2
                        )
                ));
    }

    public void sendText (String userId, int messageId) {
        moderator.setFlag(userId, constant.MODERATOR_FLAG_TEXT);

        String str = "<b>Отправьте мне свой текст \uD83D\uDC47 : </b>";

        bot.execute(new EditMessageText(userId, messageId, str)
                .parseMode(ParseMode.HTML)
                );
    }

    public void sendPicText (String userId, int messageId) {
        moderator.setFlag(userId, constant.MODERATOR_FLAG_PIC_TEXT);

        String str = "<b>Отправьте мне картинку с текстом \uD83D\uDC47 : </b>";

        bot.execute(new EditMessageText(userId, messageId, str)
                .parseMode(ParseMode.HTML)
        );
    }

    public void sendPic (String userId, int messageId) {
        moderator.setFlag(userId, constant.MODERATOR_FLAG_PIC);

        String str = "<b>Отправьте мне картинку \uD83D\uDC47 : </b>";

        bot.execute(new EditMessageText(userId, messageId, str)
                .parseMode(ParseMode.HTML)
        );
    }
}
