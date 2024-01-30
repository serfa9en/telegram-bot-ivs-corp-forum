package dataBased;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class Table extends DataBased{

    private String file;
    private String sheetName = "Tables";

    public Table(String fileName) {
        this.file = fileName;
    }

    @Override
    public void setData(String userId, String text, int num) {

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
                    count++;
                }
            }

            String[] array = new String[count];
            int ind = 0;

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    array[ind] = row.getCell(num).getStringCellValue();
                    ind++;
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

    }

    @Override
    public String getFlag(String userId) {
        return null;
    }

    @Override
    public boolean checkUser(String userId) {
        return false;
    }

}
