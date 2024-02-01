package dataBased;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Question extends DataBased{

    private String file;
    private String sheetName = "Questions";
    DataBased speaker;

    public Question(String fileName) {
        this.file = fileName;
        speaker = new Speaker(fileName);
    }

    @Override
    public void setData(String userId, String text, int num) {
        String str = "";
        String[] data = userId.split("/");
        if (num == 1) {
            // пленарка
            str = "";
        } else {
            str = data[1];
        }
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetActions = workbookTemp.getSheet(sheetName);

            XSSFRow rowAction = sheetActions.createRow(sheetActions.getPhysicalNumberOfRows());
            Cell[] cells = new Cell[4];
            cells[0] = rowAction.createCell(0);
            cells[0].setCellValue(data[0]);
            cells[1] = rowAction.createCell(1);
            cells[1].setCellValue(text);
            cells[2] = rowAction.createCell(2);
            cells[2].setCellValue(str);
            cells[3] = rowAction.createCell(3);
            cells[3].setCellValue(Integer.toString(num));

            workbookTemp.write(new FileOutputStream(file));
            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: saveQuestion");
        }
    }

    @Override
    public String getData(String userId, int num) {
        String str1 = "";
        String str2 = "";
        String str3 = "";

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    if (row.getCell(num).getStringCellValue().equals("1")) {
                        // пленарное
                        str1 += "▪\uFE0F " + row.getCell(1).getStringCellValue() + "\n";
                    } else {
                        if (speaker.getData(row.getCell(2).getStringCellValue(),5).equals("5")) {
                            str2 += "▪\uFE0F " + speaker.getData(row.getCell(2).getStringCellValue(),8)
                                    + " (" + speaker.getData(row.getCell(2).getStringCellValue(),7)
                                    + ") : " + row.getCell(1).getStringCellValue() + "\n";
                        } else {
                            str3 += "▪\uFE0F " + speaker.getData(row.getCell(2).getStringCellValue(),8)
                                    + " (" + speaker.getData(row.getCell(2).getStringCellValue(),7)
                                    + ") : " + row.getCell(1).getStringCellValue() + "\n";
                        }
                    }
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: getQuestions");
        }

        String str = "❓ <b>ВАШИ ВОПРОСЫ НА ПЛЕНАРНОМ ЗАСЕДАНИИ:</b>\n" + str1
                + "\n\n❓ <b>ВАШИ ВОПРОСЫ СПИКЕРАМ:</b>\n" +
                "<b><i>Информационная безопасность</i></b>\n" + str2 +
                "\n<b><i>Информационные технологии</i></b>\n" + str3;
        return str;
    }

    @Override
    public boolean isPerson(String userId) {
        return false;
    }

    @Override
    public void setFlag(String userId, String flag) {

    }

    @Override
    public String getFlag(String userId) {
        return null;
    }

    @Override
    public boolean checkUser(String userId) {
        return false;
    }

    @Override
    public int getCount(String data) {
        return 0;
    }

    @Override
    public String[] getDataArray(String data, int ind) {
        String[] array = new String[0];
        int count = 0;

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(3).getStringCellValue().equals("1")) {
                    // пленарка
                    if (ind == 1) count++;
                } else {
                    if (speaker.getData(row.getCell(2).getStringCellValue(),5).equals("5")) {
                        // ИБ
                        if (ind == 5) count++;
                    } else {
                        //ИТ
                        if (ind == 6) count++;
                    }
                }
            }

            array = new String[count];
            int index = 0;
            String temp = "";

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(3).getStringCellValue().equals("1")) {
                    // пленарка
                    if (ind == 1) {
                        array[index] = row.getCell(1).getStringCellValue();
                        index++;
                    }
                } else {
                    if (speaker.getData(row.getCell(2).getStringCellValue(),5).equals("5")) {
                        // ИБ
                        if (ind == 5) {
                            array[index] = "<b>" + speaker.getData(row.getCell(2).getStringCellValue(),8)
                                    + " (" + speaker.getData(row.getCell(2).getStringCellValue(),7) +
                                    "):</b> " + row.getCell(1).getStringCellValue();
                            index++;
                        }
                    } else {
                        //ИТ
                        if (ind == 6) {
                            array[index] = "<b>" + speaker.getData(row.getCell(2).getStringCellValue(),8)
                                    + " (" + speaker.getData(row.getCell(2).getStringCellValue(),7) +
                                    "):</b> " + row.getCell(1).getStringCellValue();
                            index++;
                        }
                    }
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: getQuestions");
        }

        return array;
    }

//    private String tempArray (String idSection) {
//        String str = "";
//        String temp = "";
//        String tt = "";
//        String[][] temptt = new String[0][2];
//        int count = 0;
//
//        try {
//            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
//            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);
//
//            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
//                XSSFRow row = sheetTemp.getRow(i);
//                if (row.getCell(3).getStringCellValue().equals("1")) {
//                    // пленарка
//                    if (idSection.equals("1")) {
//                        str += "▪\uFE0F " + row.getCell(1).getStringCellValue() + "\n\n";
//                    }
//                } else {
//                    if (speaker.getData(row.getCell(2).getStringCellValue(),5).equals("5")) {
//                        // ИБ
//                        if (idSection.equals("5")) {
//                           count++;
//                        }
//                    } else {
//                        //ИТ
//                        if (idSection.equals("6")) {
//                            count++;
//                        }
//                    }
//                }
//            }
//
//
//
//            workbookTemp.close();
//
//        } catch (Exception ex) {
//            System.out.println("UserBase: getQuestions");
//        }
//    }

    @Override
    public void updateDate(String userId, String tableName, int ind) {

    }

}
