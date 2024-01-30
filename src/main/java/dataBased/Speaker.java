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
    public String getData(String userId, int num) {
        return null;
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
                if (row.getCell(0).getStringCellValue().equals(userId)) {
                    flag = true;
                    break;
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


}
