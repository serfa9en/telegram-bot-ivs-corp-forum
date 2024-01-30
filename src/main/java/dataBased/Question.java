package dataBased;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class Question extends DataBased{

    private String file;
    private String sheetName = "Questions";

    public Question(String fileName) {
        this.file = fileName;
    }

    @Override
    public void setData(String userId, String text, int num) {

    }

    @Override
    public String getData(String userId, int num) {
        String str1 = "";
        String str2 = "";

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
                        str2 += "▪\uFE0F " + row.getCell(2).getStringCellValue() + ": "
                                + row.getCell(1).getStringCellValue() + "\n";
                    }
                }
            }

            workbookTemp.close();

        } catch (Exception ex) {
            System.out.println("UserBase: getQuestions");
        }

        String str = "❓ ВАШИ ВОПРОСЫ НА ПЛЕНАРНОМ ЗАСЕДАНИИ:\n" + str1
                + "\n\n❓ ВАШИ ВОПРОСЫ СПИКЕРАМ:\n" + str2;
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

}
