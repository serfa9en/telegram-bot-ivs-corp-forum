package properties;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;

public class Buttons {
    private TelegramBot bot;
    private Update update;

    Constant constant = new Constant();

    public Buttons (TelegramBot tbot) {
        this.bot = tbot;
    }

    public void setUpdate(Update update) {
        this.update = update;
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

        constant.setId("23");
        String text = MENU_SPEAKERS.toUpperCase() + "\n\n" + "Выберите секцию:";

        bot.execute(new EditMessageText (userId, messageId, text)
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

        constant.setId("24");
        constant.setSpeaker("26");

        String text = MENU_QUESTION.toUpperCase() + "\n\n" + "Выберите секцию:";
        bot.execute(new EditMessageText (userId, messageId, text)
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

        InlineKeyboardButton[] inlineKeyboardButtons3 = new InlineKeyboardButton[1];
        inlineKeyboardButtons3[0] = new InlineKeyboardButton(BACK);
        inlineKeyboardButtons3[0].callbackData(userId + "/" + messageId + "/" + constant.BACK);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                inlineKeyboardButtons0,
                inlineKeyboardButtons1,
                inlineKeyboardButtons2,
                inlineKeyboardButtons3
        );

        bot.execute(new EditMessageText (userId, messageId, "\uD83D\uDCCD КРУГЛЫЕ СТОЛЫ\n" +
                "\n" +
                "\uD83D\uDCCE " + constant.TABLE_NAME_1 + "\n" +
                "Зал Edison\n" +
                "    • Депутат Государственной Думы, координатор федерального партпроекта «Цифровая Россия» Немкин А.И.; \n" +
                "    • Генеральный директор фонда «Цифровая Долина Прикамья», руководитель федерального штаба партийного проекта «Цифровая Россия» Ландарь А.С.\n" +
                "    • Министр ЦР Республики Калмыкия Этеев А.П.\n" +
                "    • Министр ЦР, ИТ и связей Рязанской области Ульянов А.Ю. \n" +
                "    • Министр ЦР и информационно-коммуникационных технологий Новгородской области Киблер М.В.\n" +
                "    • Руководитель направления развития Ассоциации социальных предпринимателей Астраханской области Махринский С.В.\n" +
                "\n" +
                "\uD83D\uDCCE " + constant.TABLE_NAME_2 + "\n" +
                "Зал Edison\n" +
                "«АйТи Бастион» — производитель системы контроля действий поставщиков ИТ-услуг СКДПУ НТ." +
                "Компания занимает более 50% российского рынка PAM-решений (Privileged Access Management). " +
                "СКДПУ НТ является единственной на данный момент PAM-системой, сертифицированной ФСТЭК России. " +
                "Отечественный разработчик решений в области информационной безопасности.\n" +
                "\n" +
                "\uD83D\uDCCE " + constant.TABLE_NAME_3 + "\n" +
                "Зал Информационная безопасность\n" +
                "\n")
                //+ getUserTables(userId))
                .replyMarkup(inlineKeyboard));
    }


    public void createButtonsSpeakers (String userId, int messageId, String idSection) {
//        InlineKeyboardButton[][] inlineButtons = new InlineKeyboardButton[speaker.getCount(idSection)+2][1];
//        for(int i = 0; i < inlineButtons.length-2; i++) {
//            inlineButtons[i][0] = new InlineKeyboardButton(speaker.getName(idSection,speaker.getId(idSection,i)));
//            inlineButtons[i][0].callbackData(userId + "/" + messageId + "/" + speaker.getId(idSection,i));
//        }
//
//        String text = "";
//        if (idSection.equals("5")) {
//            text =  btnName.SPEAKERS_SECTIONS[0].toUpperCase();
//        } else {
//            text =  btnName.SPEAKERS_SECTIONS[1].toUpperCase();
//        }
//
//        inlineButtons[inlineButtons.length-2][0] = new InlineKeyboardButton(btnName.BACK);
//        inlineButtons[inlineButtons.length-2][0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_BACK);
//        inlineButtons[inlineButtons.length-1][0] = new InlineKeyboardButton(btnName.MENU);
//        inlineButtons[inlineButtons.length-1][0].callbackData(userId + "/" + messageId + "/" + constant.MENU);
//
//        bot.execute(new EditMessageText (userId, messageId, text + ":\n\n" + getSpeakerCard(idSection))
//                .replyMarkup(
//                        new InlineKeyboardMarkup(
//                                inlineButtons
//                        )
//                ));
    }

    public void createAskQuestion(String userId, int messageId) {
//        setAction(userId,constant.USER_FLAGS_QUESTION_PLENARY);
//        //System.out.println(constant.PERSON);
//
//        String text = "";
//        if (constant.PERSON.equals("26")) {
//            text = "Данный вопрос будет рассматриваться на пленарном заседании";
//        } else {
//            text = "Спикер: " + speaker.getNameId(constant.PERSON) + "\n"
//                    + speaker.getDescriptionId(constant.PERSON);
//        }
//
//        text += "\n\n";
//        text += "❗\uFE0F Отправьте Ваш вопрос:";
//
//        InlineKeyboardButton[] inlineButtons = new InlineKeyboardButton[1];
//        inlineButtons[0] = new InlineKeyboardButton(btnName.CANCEL_QUEST);
//        inlineButtons[0].callbackData(userId + "/" + messageId + "/" + constant.CANCEL_QUEST);
//
//        bot.execute(new EditMessageText(userId, messageId, text)
//                .replyMarkup(
//                        new InlineKeyboardMarkup(
//                                inlineButtons
//                        )
//                ));
    }

    public void createSpeakerCard (String userId, int messageId, String idSpeaker) {
        // задать вопрос спикеру
        // System.out.println(idSpeaker);

//        InlineKeyboardButton[] inlineKeyboardButtons0 = new InlineKeyboardButton[1];
//        inlineKeyboardButtons0[0] = new InlineKeyboardButton("Задать вопрос");
//        inlineKeyboardButtons0[0].callbackData(userId + "/" + messageId + "/" + constant.QUEST_SPEAKER);
//
//        InlineKeyboardButton[] inlineKeyboardButtons1 = new InlineKeyboardButton[1];
//        inlineKeyboardButtons1[0] = new InlineKeyboardButton(BACK);
//        inlineKeyboardButtons1[0].callbackData(userId + "/" + messageId + "/" + constant.SPEAKER_BACK);
//
//        InlineKeyboardButton[] inlineKeyboardButtons2 = new InlineKeyboardButton[1];
//        inlineKeyboardButtons2[0] = new InlineKeyboardButton(MENU);
//        inlineKeyboardButtons2[0].callbackData(userId + "/" + messageId + "/" + constant.MENU);
//
//        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
//                inlineKeyboardButtons0,
//                inlineKeyboardButtons1,
//                inlineKeyboardButtons2
//        );
//
//        String str = speaker.getNameId(idSpeaker) + "\n" + speaker.getDescriptionId(idSpeaker);
//        constant.setSpeaker(idSpeaker);
//
//        bot.execute(new EditMessageText(userId, messageId, str)
//                .replyMarkup(inlineKeyboard));
    }

}
