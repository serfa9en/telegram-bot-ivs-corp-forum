package dataBased;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class Speaker extends DataBased {

    private String file;
    private String sheetName = "Speaker";

    public Speaker(String fileUser) {
        this.file = fileUser;
    }

    @Override
    public void setData(String userId, String text, int num) {

    }

    @Override
    public String getData(String idSpeaker, int num) {
        String str = "";

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(6).getStringCellValue().equals(idSpeaker)) {
                    str = row.getCell(num).getStringCellValue();
                    break;
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: Speaker: getData");
        }

        return str;
    }

    @Override
    public boolean isPerson(String userId) {
        // если спикер, то возвращает true
        boolean flag = false;

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if(row.getCell(0) != null) {
                    if (row.getCell(0).getStringCellValue().equals(userId)) {
                        flag = true;
                        break;
                    }
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("DataBase: Speaker: isPerson");
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
    public int getCount(String idSection) {
        int count = 0;

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(5).getStringCellValue().equals(idSection)) {
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
    public String[] getDataArray(String idSection, int ind) {
        String[] str = new String[5];
        int num = 5;

        try {
            XSSFWorkbook workbookTemp = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheetTemp = workbookTemp.getSheet(sheetName);

            for (int i = 1; i < sheetTemp.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheetTemp.getRow(i);
                if (row.getCell(num).getStringCellValue().equals(idSection) &&
                    row.getCell(num-1).getStringCellValue().equals(Integer.toString(ind))) {
                    for(int j = 0; j < str.length; j++) {
                        str[j] = row.getCell(num + (j + 1)).getStringCellValue();
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

    }


}
