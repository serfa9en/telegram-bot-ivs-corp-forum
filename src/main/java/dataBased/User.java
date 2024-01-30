package dataBased;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class User extends DataBased{
    private String file;
    private String sheetName = "User";
    private int countFieldUsers = 5;

    public User(String fileName) {
        this.file = fileName;
    }

    @Override
    public void setData(String userId, String text, int num) {
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            XSSFRow row = null;
            if (num == 0) {
                row = sheetTemp.createRow(sheetTemp.getPhysicalNumberOfRows());
            } else {
                row = sheetTemp.getRow(sheetTemp.getLastRowNum());
            }
            Cell column = row.createCell(num);
            column.setCellValue(text);
            sheetTemp.autoSizeColumn(num);

            workbookTemp.write(new FileOutputStream(file));
            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: User: setData (" + userId + " - " + text + " - " + num + ")");
        }
    }

    @Override
    public boolean isPerson(String userId) {
        // если пользователь есть, то возвращает true
        boolean flag = false;

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    flag = true;
                    break;
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: User: isPerson");
        }

        return flag;
    }

    // установка флага действий
    @Override
    public void setFlag(String userId, String flag) {

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetActions = workbookTemp.getSheet(sheetName);

            for(int i = 1; i < sheetActions.getPhysicalNumberOfRows(); i++) {
                XSSFRow rowAction = sheetActions.getRow(i);
                if (rowAction.getCell(0) != null) {
                    if (rowAction.getCell(0).getStringCellValue().equals(userId)) {
                        rowAction.getCell(5).setCellValue(flag);
                        break;
                    }
                }
            }

            workbookTemp.write(new FileOutputStream(file));
            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: User: setFlag");
        }
    }

    // заполнена ли информация о пользователе
    @Override
    public boolean checkUser(String userId) {
        boolean flag = false;
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    if (row.getCell(4) != null) {
                        if (!row.getCell(4).getStringCellValue().equals("")) {
                            flag = true;
                            break;
                        }
                    }
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: User: checkUser");
        }
        return flag;
    }

    // получение значения флага действий
    @Override
    public String getFlag(String userId) {
        String flag = "";

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    flag = row.getCell(5).getStringCellValue();
                    break;
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: User: getFlag");
        }

        return flag;
    }

    public void addNewUser(String[] str) {
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet("Users");

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                Cell[] column = new Cell[countFieldUsers];
                if (row.getCell(0).getStringCellValue().equals(str[0])) {
                    for (int j = 1; j < countFieldUsers; j++) {
                        column[j] = row.getCell(j);
                        column[j].setCellValue(str[j]);
                        sheetTemp.autoSizeColumn(j);
                    }
                    break;
                }
            }

            workbookTemp.write(new FileOutputStream(file));
            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: addNewUser");
        }
    }

    // добавление информации о пользователе
    public void addName(String userId, String text) {
        String[] temp = new String[countFieldUsers];
        int numberRow = 0;

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbook.getSheet("Users");

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    Cell column = row.createCell(4);
                    column.setCellValue(text);
                    sheetTemp.autoSizeColumn(4);
                    break;
                }
            }

            workbook.write(new FileOutputStream(file));
            workbook.close();

        } catch (Exception ex) {
            System.out.println("UserBase: addInfoUser");
        }
    }

    // получение информации пользователя по индексу
    public String getData(String userId, int index) {
        String data = "";

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    if (row.getCell(index) != null) {
                        data = row.getCell(index).getStringCellValue();
                    } else {
                        data = " ";
                    }
                    break;
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: getData");
        }

        return data;
    }

    public boolean isUserTable (String userId, String text) {
        boolean flag = true;
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet("Tables");

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    if (row.getCell(1).getStringCellValue().equals(text)) {
                        flag = false;
                        break;
                    }
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: isUserTable");
        }
        return flag;
    }

    private String getNameSpeaker(String id) {
        String str = "";
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet("Speakers");

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(id)) {
                    str = row.getCell(5).getStringCellValue();
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: getNameSpeaker");
        }
        return str;
    }

    public void setTable(String userId, String text) {
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetActions = workbookTemp.getSheet("Tables");

            XSSFRow rowAction = sheetActions.createRow(sheetActions.getPhysicalNumberOfRows());
            Cell[] cells = new Cell[2];
            cells[0] = rowAction.createCell(0);
            cells[0].setCellValue(userId);
            cells[1] = rowAction.createCell(1);
            cells[1].setCellValue(text);

            workbookTemp.write(new FileOutputStream(file));
            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: setTable");
        }
    }

    public String getSpeakerId(String idSpeaker) {
        String idTg = "";

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet("Speakers");

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(4).getStringCellValue().equals(idSpeaker)) {
                    idTg = row.getCell(0).getStringCellValue();
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: getSpeakerId");
        }

        return idTg;
    }

    public void saveQuestion(String userId, String question, String toSmt) {
        String id = "";
        if (toSmt.equals("26")) {
            id = "1";
        } else {
            id = "0";
        }
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetActions = workbookTemp.getSheet("Questions");

            XSSFRow rowAction = sheetActions.createRow(sheetActions.getPhysicalNumberOfRows());
            Cell[] cells = new Cell[4];
            cells[0] = rowAction.createCell(0);
            cells[0].setCellValue(userId);
            cells[1] = rowAction.createCell(1);
            cells[1].setCellValue(question);
            cells[2] = rowAction.createCell(2);
            cells[2].setCellValue(getSpeakerId(toSmt));
            cells[3] = rowAction.createCell(3);
            cells[3].setCellValue(id);

            workbookTemp.write(new FileOutputStream(file));
            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: saveQuestion");
        }
    }

}
