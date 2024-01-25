package properties;

public class Speaker {
    String[][] speakers = {
            // id секции, id спикера, картинка, ФИО, должность

            { "5", "10", "AgACAgIAAxkBAAIB4GWwpFKjMk_UPB9GRVQWP5y27noCAAJ01zEb1CCASYkcKZsD1YoXAQADAgADcwADNAQ", "Алтухов Владимир", "pуководитель технического центра ООО \"Ай Ти Бастион\"", "0" },
            { "5", "11", "AgACAgIAAxkBAAIB4mWwpGMFWmhIK4KPfVAoyj7QmUeqAAJ21zEb1CCASXKmUirdUYUxAQADAgADcwADNAQ", "Дятлов Иван", "ведущий менеджер по работе с партнерами Центра защиты информации ООО «Конфидент»", "1" },
            { "5", "12", "AgACAgIAAxkBAAIB4WWwpFhIAXci6D_AOEpS9BY5ZY3aAAJ11zEb1CCASQWWo1BlZrW6AQADAgADcwADNAQ", "Татьяна Мороз", "менеджер по продажам UserGate", "2" },
            { "5", "13", "AgACAgIAAxkBAAIB5GWwpG2myVk_Rox5YabWSmFs3EARAAJ41zEb1CCASf48aO4B7PAqAQADAgADcwADNAQ", "Михаил Самсонов", "эксперт центра компетенций Positive Technologies", "3" },

            { "6", "15", "AgACAgIAAxkBAAIB5mWwpHcTe037MbWqK7c0fKsrM5udAAJ71zEb1CCASWjVERef2hKtAQADAgADcwADNAQ", "Кривоносов Евгений", "Генеральный директор ООО «СДИ СОФТ»", "0" },
            { "6", "16", "AgACAgIAAxkBAAIB5mWwpHcTe037MbWqK7c0fKsrM5udAAJ71zEb1CCASWjVERef2hKtAQADAgADcwADNAQ", "Сергей Пахомов", "системный инженер СИСТЭМ ЭЛЕКТРИК", "1" },
            { "6", "17", "AgACAgIAAxkBAAIB5mWwpHcTe037MbWqK7c0fKsrM5udAAJ71zEb1CCASWjVERef2hKtAQADAgADcwADNAQ", "Вячеслав Кадомский", "директор по развитию НТЦ ИТ РОСА", "2" },
            { "6", "18", "AgACAgIAAxkBAAIB5mWwpHcTe037MbWqK7c0fKsrM5udAAJ71zEb1CCASWjVERef2hKtAQADAgADcwADNAQ", "Субачев Максим Алексеевич", "CEO", "3" },
            { "6", "19", "AgACAgIAAxkBAAIB5mWwpHcTe037MbWqK7c0fKsrM5udAAJ71zEb1CCASWjVERef2hKtAQADAgADcwADNAQ", "Владимир Богачев", "руководитель отдела по работе с ключевыми клиентами","4" },

            /*
            { "id_section", "16", "AgACAgIAAxkBAAIB42WwpGcHFv4CmOvkJlyVYXY69EgpAAJ31zEb1CCASbwrIYNnB8A-AQADAgADcwADNAQ", "Ландырь А", "description" },
            { "id_section", "18", "AgACAgIAAxkBAAIB5WWwpHJ6Ey8eASZ8il9i4DjByN0LAAJ51zEb1CCASeeHVHnfDQOAAQADAgADcwADNAQ", "Немкин А.И.", "description" },
            { "id_section", "20", "AgACAgIAAxkBAAIB52WwpHvqVOa_DcvLp3XvLGS3uYXtAAJ61zEb1CCASS6JKjTGmPXEAQADAgADcwADNAQ", "Шиловских Петр Александрович", "description" }
         */
    };

    public int getCount (String idSection) {
        int count = 0;
        for (int i = 0; i < speakers.length; i++) {
            if (speakers[i][0].equals(idSection)) {
                count++;
            }
        }
        return count;
    }

    public String getId (String idSection, int id) {
        String str = "";
        for (int i = 0; i < speakers.length; i++) {
            if (speakers[i][0].equals(idSection) &&
                    speakers[i][5].equals(Integer.toString(id))) {
                str = speakers[i][1];
            }
        }
        return str;
    }

    public String getName (String idSection, String idSpeaker) {
        String str = "";
        for (int i = 0; i < speakers.length; i++) {
            if (speakers[i][1].equals(idSpeaker) && speakers[i][0].equals(idSection)) {
                str = speakers[i][3];
            }
        }
        return str;
    }

    public String getNameId (String idSpeaker) {
        String str = "";
        for (int i = 0; i < speakers.length; i++) {
            if (speakers[i][1].equals(idSpeaker)) {
                str = speakers[i][3];
            }
        }
        return str;
    }

    public String getIdPic (String idSection, String idSpeaker) {
        String str = "";
        for (int i = 0; i < speakers.length; i++) {
            if (speakers[i][1].equals(idSpeaker) && speakers[i][0].equals(idSection)) {
                str = speakers[i][2];
            }
        }
        return str;
    }

    public String getDescription (String idSection, String idSpeaker) {
        String str = "";
        for (int i = 0; i < speakers.length; i++) {
            if (speakers[i][1].equals(idSpeaker) && speakers[i][0].equals(idSection)) {
                str = speakers[i][4];
            }
        }
        return str;
    }

    public String getDescriptionId (String idSpeaker) {
        String str = "";
        for (int i = 0; i < speakers.length; i++) {
            if (speakers[i][1].equals(idSpeaker)) {
                str = speakers[i][4];
            }
        }
        return str;
    }

}
