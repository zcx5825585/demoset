package zcx.com.example.excel.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelGenerator {

    public static <T> HSSFWorkbook createSheet(HSSFWorkbook workbook, List<T> data, Class<T> clazz) throws IllegalAccessException {
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        if (sheetAnno == null) {
            return null;
        }
        String sheetName = "".equals(sheetAnno.sheetName()) ? clazz.getSimpleName() : sheetAnno.sheetName();
        //创建工作表对象
        HSSFSheet sheet = workbook.createSheet(sheetName);//设置sheet的Name

        ClassInfo classInfo = new ClassInfo(clazz);

        //创建工作表的行
        int index = 0;
        HSSFRow row = sheet.createRow(index);//设置第一行，从零开始
        for (int i = 0; i < classInfo.getTitles().size(); i++) {
            String columnName = classInfo.getTitles().get(i);
            row.createCell(i).setCellValue(columnName);
        }
        index++;
        for (T datum : data) {
            HSSFRow newRow = sheet.createRow(index);//设置第二行，从一开始
            for (int i = 0; i < classInfo.getTitles().size(); i++) {
                HSSFCell cell = newRow.createCell(i);
                String columnName = classInfo.getTitles().get(i);
                cellMaker(cell, classInfo.getFieldMap().get(columnName), classInfo.getAnnoMap().get(columnName), datum);
            }
            index++;
        }
        return workbook;
    }

    private static <T> HSSFCell cellMaker(HSSFCell cell, Field field, ExcelColumn columnAnno, T datum) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(datum);
        //value为空 并且未设默认值 将单元格设为空白
        if (value == null && "".equals(columnAnno.defaultValue())) {
            cell.setCellType(CellType.BLANK);
        } else {
            //value为空 但设了默认值，将value修改为默认值
            if (value == null) {
                value = columnAnno.defaultValue();
            }
            //此时value有值 设置单元格格式 对value进行相应转换后填入单元格
            switch (columnAnno.valueType()) {
                case STRING:
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(String.valueOf(value));
                    break;
                default:
                    cell.setCellType(CellType.BLANK);
            }
        }
        return cell;
    }


    public static <T> String createCsv(List<T> data, Class<T> clazz) throws IllegalAccessException {
        ClassInfo classInfo = new ClassInfo(clazz);

        StringBuilder file = new StringBuilder();
        StringBuilder title = new StringBuilder();
        //创建工作表的行
        for (String columnName : classInfo.getTitles()) {
            title.append(",").append(columnName);
        }
        file.append(title.substring(1)).append("\n");

        for (T datum : data) {
            StringBuilder row = new StringBuilder();
            for (String columnName : classInfo.getTitles()) {
                String cell = stringMaker(classInfo.getFieldMap().get(columnName), classInfo.getAnnoMap().get(columnName), datum);
                row.append(",").append(cell);
            }
            file.append(row.substring(1)).append("\n");
        }
        return file.toString();
    }

    private static <T> String stringMaker(Field field, ExcelColumn columnAnno, T datum) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(datum);
        //value为空 并且设置了默认值 将value修改为默认值
        if (value == null && !"".equals(columnAnno.defaultValue())) {
            value = columnAnno.defaultValue();
        }
        return String.valueOf(value);
    }
}
