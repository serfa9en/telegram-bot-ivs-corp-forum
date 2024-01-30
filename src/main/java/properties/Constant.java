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

    public  final String SPEAKERS_SECURITY = "5";
    public  final String SPEAKERS_TECNOLOGY = "6";
    public  final String QUEST_PLENARY = "7";

    public final String QUEST_SPEAKER = "27";



    public  final String TABLE_1 = "8";
    public  final String TABLE_2 = "9";
    public final String TABLE_3 = "20";

    public final String BACK = "21";
    public final String MENU = "22";
    public String SPEAKER_BACK = "23";

    public void setId (String id) {
        this.SPEAKER_BACK = id;
    }

    public final String CANCEL_QUEST = "25";

    //------------------------------------------------------

    public final String TABLE_NAME_1 = "13:00 - 15:00 — Цифровая Россия";
    public final String TABLE_NAME_2 = "15:30 - 17:30 — АйТи БАСТИОН";
    public final String TABLE_NAME_3 = "16:45 – 18:15 — СДИ Софт";

    //---------------------------------------------------------
    // указать айдишники
    public String PERSON = "";
    public void setSpeaker (String id) {
        this.PERSON = id;
    }

}
