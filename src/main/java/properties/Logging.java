package properties;

import dataBased.DataBased;
import dataBased.User;

import java.io.*;
import java.util.Calendar;
import java.util.Formatter;

public class Logging {

    private String fileName;
    private String fileLog = "C:/Users/mbelyaeva/IdeaProjects/telegram-bot-ivs-corp-forum-version2/telegram-bot-ivs-corp-forum-version2/src/main/java/files/Logging.txt";

    public Logging(String fileName) {
        this.fileName = fileName;
    }

    public String createLogText (String userId, String text) {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        fmt = new Formatter();
        fmt.format("%tc", cal);
        DataBased userDb = new User(fileName);

        String logText = "";
        logText = "[" + fmt + "]:" +
                userId + "[" + userDb.getData(userId, 1) + "/" + userDb.getData(userId, 2) + "/" + userDb.getData(userId, 3) + "]:" +
                "sendText[" + text + "]\n";
        return logText;
    }

    public void saveLogText(String userId, String text) {
//        Formatter fmt = new Formatter();
//        Calendar cal = Calendar.getInstance();
//        fmt = new Formatter();
//        fmt.format("%tc", cal);
//        UserBase userDb = new UserBase(fileName);
//
//        String logText = "";
//        logText = "[" + fmt + "]:" +
//                userId + "[" + userDb.getData(userId, 1) + "/" + userDb.getData(userId, 2) + "/" + userDb.getData(userId, 3) + "]:[" +
//                userDb.getData(userId, 4) + "/" + userDb.getData(userId, 5) + "/" + userDb.getData(userId, 6) + "]:" +
//        "sendText[" + text + "]\n";

//        System.out.println(logText);

        addLog(createLogText(userId, text));
    }

    public void saveLogButton(String userId, String textQuestion) {

//        System.out.println(logText);
        addLog(createLogButton(userId, textQuestion));
    }

    public void addLog (String text) {
        try {
            FileWriter writer = new FileWriter(fileLog, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(text);
            bufferWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public String createLogButton(String userId, String textQuestion) {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        fmt = new Formatter();
        fmt.format("%tc", cal);
        DataBased userDb = new User(fileName);

        String logText = "";
        logText = "[" + fmt + "]:" +
                userId + "[" + userDb.getData(userId, 1) + "/" + userDb.getData(userId, 2) + "/" + userDb.getData(userId, 3) + "]:" +
                "clickButton[" + textQuestion + "/"  + "]\n";
        return logText;
    }
}
