package dataBased;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Table extends DataBased{

    private String file;
    private String sheetName = "Tables";

    public Table(String fileName) {
        this.file = fileName;
    }

    @Override
    public void setData(String userId, String text, int num) {
        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            XSSFRow row = sheetTemp.createRow(sheetTemp.getPhysicalNumberOfRows());
            Cell column1 = row.createCell(0);
            column1.setCellValue(userId);
            sheetTemp.autoSizeColumn(0);

            Cell column2 = row.createCell(1);
            column2.setCellValue(text);
            sheetTemp.autoSizeColumn(1);

            Cell column3 = row.createCell(2);
            column3.setCellValue(Integer.toString(num));
            sheetTemp.autoSizeColumn(2);

            workbookTemp.write(new FileOutputStream(file));
            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: User: setData (" + userId + " - " + text + " - " + num + ")");
        }
    }

    @Override
    public String getData(String userId, int num) {
        String str = "";
        int count = 0;

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    if (row.getCell(2).getStringCellValue().equals("1")) {
                        count++;
                    }
                }
            }

            String[] array = new String[count];
            int ind = 0;

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    if (row.getCell(2).getStringCellValue().equals("1")) {
                        array[ind] = row.getCell(num).getStringCellValue();
                        ind++;
                    }
                }
            }

            array = sort(array);

            for (int i = 0; i < array.length; i++) {
                str += "▪\uFE0F " + array[i] + "\n";
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: getTables");
        }
        return str;
    }

    private String[] sort (String[] array) {
        String[] str = new String[array.length];

        for (int j = 0; j < array.length; j++) {
            String temp = array[j];
            int t = Integer.parseInt(array[j].substring(0,2));
            for (int i = j+1; i < array.length; i++) {
                if (t > Integer.parseInt(array[i].substring(0,2))) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            str[j] = temp;
        }

        return str;
    }

    @Override
    public boolean isPerson(String userId) {
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

    @Override
    public void setFlag(String userId, String flag) {
        String[] str = flag.split("/");
        //str[0] - tableName
        //str[1] - flag

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetActions = workbookTemp.getSheet(sheetName);

            for(int i = 1; i < sheetActions.getPhysicalNumberOfRows(); i++) {
                XSSFRow rowAction = sheetActions.getRow(i);
                if (rowAction.getCell(0) != null) {
                    if (rowAction.getCell(0).getStringCellValue().equals(userId)) {
                        if (rowAction.getCell(1).getStringCellValue().equals(str[0])) {
                            rowAction.getCell(2).setCellValue(str[1]);
                            break;
                        }
                    }
                }
            }

            workbookTemp.write(new FileOutputStream(file));
            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: User: setFlag");
        }
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
        int count = 0;

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(data)) {
                    count++;
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: Speaker: getCount");
        }

        return count;
    }

    @Override
    public String[] getDataArray(String data, int count) {
        int ii = 0;
        int countTemp = 0;
        String[] str = new String[0];

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(data)) {
                    if (row.getCell(2).getStringCellValue().equals(Integer.toString(count))) {
                        countTemp++;
                    }
                }
            }

           str = new String[countTemp];

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(data)) {
                    if (row.getCell(2).getStringCellValue().equals(Integer.toString(count))) {
                        str[ii] = row.getCell(1).getStringCellValue();
                        ii++;
                    }
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: Speaker: getDataArray");
        }

        return str;
    }

    @Override
    public void updateDate(String userId, String tableName, int ind) {
//        try {
//            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
//            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);
//
//            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
//                XSSFRow row = sheetTemp.getRow(i);
//                if (row.getCell(0).getStringCellValue().equals(userId)) {
//                    if (row.getCell(1).getStringCellValue().equals(tableName)) {
//
//                    }
//                }
//            }
//
//            Cell column1 = row.createCell(0);
//            column1.setCellValue(userId);
//            sheetTemp.autoSizeColumn(0);
//
//            Cell column2 = row.createCell(num);
//            column2.setCellValue(text);
//            sheetTemp.autoSizeColumn(num);
//
//            workbookTemp.write(new FileOutputStream(file));
//            workbookTemp.close();
//
//        } catch (Exception ex) {
//            System.out.println("DataBase: User: setData (" + userId + " - " + text + " - " + num + ")");
//        }
    }

}
