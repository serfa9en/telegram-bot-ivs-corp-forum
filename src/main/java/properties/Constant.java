package properties;

public class Constant {

    // text greeting
    public final String REGISTRY = "❗\uFE0F Пожалуйста, зарегистрируйтесь:\n" +
            "\n" +
            "▪\uFE0F Введите ФИО\n" +
            "▪\uFE0F Название компании";

    // ФЛАГИ---------------------------------------------------------
    // Флаги пользователя
    public final String USER_FLAGS_DEFAULT = "0";
    public final String USER_FLAGS_REGISTRY = "1";
    public final String USER_FLAGS_QUESTION_PLENARY = "2";


    // ID КНОПОК -------------------------------------------------------
    public  final String MENU_PROGRAM = "1";
    public  final String MENU_SPEAKERS = "2";
    public  final String MENU_QUEST = "3";
    public  final String MENU_TABLE = "4";
    public final String DELETE_TABLE_MENU = "38";

    public  final String SPEAKERS_SECURITY = "5";
    public  final String SPEAKERS_TECNOLOGY = "6";
    public  final String QUEST_PLENARY = "7";

    public final String QUEST_SPEAKER = "29";
    // пленарка 33



    public  String TABLE_1 = "8";
    public  String TABLE_2 = "9";
    public String TABLE_3 = "10";
    public String DELETE_TABLE = "";
    public void setDeleteTable (String id) {
        this.DELETE_TABLE = id;
    }
    public void setDeleteTable1 (String id) {
        this.TABLE_1 = id;
    }
    public void setDeleteTable2 (String id) {
        this.TABLE_2 = id;
    }
    public void setDeleteTable3 (String id) {
        this.TABLE_3 = id;
    }

    public final String BACK = "30";
    public final String MENU = "31";
    public String SPEAKER_BACK = "27"; //27, 28

    public void setIdBack (String id) {
        this.SPEAKER_BACK = id;
    }

    public final String CANCEL_QUEST = "32";

    //------------------------------------------------------

    public final String TABLE_NAME_1 = "13:00 - 14:30 — Цифровая Россия";
    public final String TABLE_NAME_2 = "14:30 - 17:30 — АйТи БАСТИОН";
    public final String TABLE_NAME_3 = "16:45 – 18:00 — СДИ Софт";

    //---------------------------------------------------------
    // указать айдишники
    public String QUESTION_TYPE = "";
    public void setType (String type) {
        this.QUESTION_TYPE = type;
    }
    public String PERSON = "";
    public void setSpeaker (String id) {
        this.PERSON = id;
    }

    //---------------------------------------------------------------------
    public final String MENU_MODERATOR_PLENARY = "111";
    public final String MENU_MODERATOR_SAFE = "222";
    public final String MENU_MODERATOR_TECH = "333";
    public final String MENU_MODERATOR = "444";

    public final String MENU_SEND_SEND = "555";

    public final String MODERATOR_SENT_TEXT = "666";
    public final String MODERATOR_SENT_PIC_TEXT = "888";
    public final String MODERATOR_SENT_PIC = "777";
    public final String MODERATOR_FLAG_TEXT = "1";
    public final String MODERATOR_FLAG_PIC = "2";
    public final String MODERATOR_FLAG_PIC_TEXT = "3";
    public final String MODERATOR_FLAG_DEFAULT = "0";


    public final String TEXT_TABLE =
            "\n" +
            "\uD83D\uDCCE <b>" + TABLE_NAME_1 + "</b>\n" +
            "<i><b>Зал Edison</b></i>\n" +
            "    • Депутат Государственной Думы, координатор федерального партпроекта «Цифровая Россия» Немкин А.И.; \n" +
            "    • Генеральный директор фонда «Цифровая Долина Прикамья», руководитель федерального штаба партийного проекта «Цифровая Россия» Ландарь А.С.\n" +
            "    • Министр ЦР Республики Калмыкия Этеев А.П.\n" +
            "    • Министр ЦР, ИТ и связей Рязанской области Ульянов А.Ю. \n" +
            "    • Министр ЦР и информационно-коммуникационных технологий Новгородской области Киблер М.В.\n" +
            "    • Руководитель направления развития Ассоциации социальных предпринимателей Астраханской области Махринский С.В.\n" +
            "\n" +
            "\uD83D\uDCCE <b>" + TABLE_NAME_2 + "</b>\n" +
            "<i><b>Зал Edison</b></i>\n" +
            "«АйТи Бастион» — производитель системы контроля действий поставщиков ИТ-услуг СКДПУ НТ." +
            "Компания занимает более 50% российского рынка PAM-решений (Privileged Access Management). " +
            "СКДПУ НТ является единственной на данный момент PAM-системой, сертифицированной ФСТЭК России. " +
            "Отечественный разработчик решений в области информационной безопасности.\n" +
            "\n" +
            "\uD83D\uDCCE <b>" + TABLE_NAME_3 + "</b>\n" +
            "<i><b>Зал 'Информационная безопасность'</b></i>\n" +
            "Основа надежного и рационального управления ЦОДом\n" +
            "\n";

}
