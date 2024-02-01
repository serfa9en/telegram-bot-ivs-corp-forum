package dataBased;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class Moderator extends DataBased{

    private String file;
    private String sheetName = "Moderator";

    public Moderator(String fileUser) {
        this.file = fileUser;
    }

    @Override
    public void setData(String userId, String text, int num) {

    }

    @Override
    public String getData(String userId, int num) {
        return null;
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
            System.out.println("DataBase: Moderator: isPerson");
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

    @Override
    public int getCount(String data) {
        int count = 0;

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            count = sheetTemp.getPhysicalNumberOfRows();

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: getQuestions");
        }

        return count;
    }

    @Override
    public String[] getDataArray(String data, int ind) {
        String[] str = new String[ind];

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                str[i-1] = row.getCell(0).getStringCellValue();
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: Speaker: getDataArray");
        }

        return str;
    }

    @Override
    public void updateDate(String userId, String tableName, int ind) {

    }

}
