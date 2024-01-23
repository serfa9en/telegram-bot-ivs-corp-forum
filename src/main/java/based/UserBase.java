package based;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class UserBase {
    private String file;
    private int countFieldUsers = 5;
    public UserBase(String fileName) {
        this.file = fileName;
    }

    // установка флага действий
    public void setFlag(String userId, String flag) {
        boolean tempFlag = false;

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetActions = workbookTemp.getSheet("Users");

            for(int i = 1; i < sheetActions.getPhysicalNumberOfRows(); i++) {
                XSSFRow rowAction = sheetActions.getRow(i);
                if (rowAction.getCell(0) != null) {
                    if (rowAction.getCell(0).getStringCellValue().equals(userId)) {
                        rowAction.getCell(5).setCellValue(flag);
                        tempFlag = true;
                        break;
                    }
                }
            }

            if (!tempFlag) {
                XSSFRow rowAction = sheetActions.createRow(sheetActions.getPhysicalNumberOfRows());
                Cell[] cells = new Cell[6];
                cells[0] = rowAction.createCell(0);
                cells[0].setCellValue(userId);
                cells[1] = rowAction.createCell(1);
                cells[1].setCellValue("");
                cells[2] = rowAction.createCell(2);
                cells[2].setCellValue("");
                cells[3] = rowAction.createCell(3);
                cells[3].setCellValue("");
                cells[4] = rowAction.createCell(4);
                cells[4].setCellValue("");
                cells[5] = rowAction.createCell(5);
                cells[5].setCellValue(flag);
            }

            workbookTemp.write(new FileOutputStream(file));
            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: setFlag");
        }
    }

    // получение значения флага действий
    public String getFlag(String userId) {
        String flag = "";

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet("Users");

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    flag = row.getCell(5).getStringCellValue();
                    break;
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: getFlag");
        }

        return flag;
    }

    // заполнена ли информация о пользователе
    public boolean checkUser(String userId) {
        boolean flag = false;
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet("Users");

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                    XSSFRow row = sheetTemp.getRow(i);
                    if (row.getCell(0).getStringCellValue().equals(userId)) {
                        for (int j = 1; j < countFieldUsers; j++) {
                            if (row.getCell(j) != null) {
                                if (row.getCell(j).getStringCellValue().equals("")) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: checkUser");
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
            XSSFSheet sheetTemp = workbookTemp.getSheet("Users");

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
}
