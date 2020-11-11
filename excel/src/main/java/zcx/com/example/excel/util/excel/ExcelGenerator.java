package zcx.com.example.excel.util.excel;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelGenerator {

    public static <T> List<T> mapSheet(HSSFWorkbook workbook, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        ObjectExcelInfo classInfo = new ObjectExcelInfo(clazz);
        if (!classInfo.isExcelEntity()) {
            //未添加注解
            return new ArrayList<>();
        }

        //获取对应的sheet
        HSSFSheet sheet = workbook.getSheet(classInfo.getSheetName());//设置sheet的Name

        //获取工作表的行
        int index = 0;
        HSSFRow title = sheet.getRow(index);//获取第一行
        List<String> sheetTitles = new ArrayList<>();
        for (int i = 0; i < title.getLastCellNum(); i++) {
            HSSFCell cell = title.getCell(i);
            //如果表头是string格式且表头文字包含在class的titles中，则将该列加入转换列表，否则在该位置填null用于跳过该列
            if (CellType.STRING.equals(cell.getCellType()) && classInfo.getTitles().contains(cell.getStringCellValue())) {
                sheetTitles.add(cell.getStringCellValue());
            } else {
                sheetTitles.add(null);
            }
        }
        index++;

        List<T> data = new ArrayList<>();
        while (index < sheet.getLastRowNum() + 1) {
            HSSFRow row = sheet.getRow(index);
            T datum = clazz.newInstance();
            for (int i = 0; i < sheetTitles.size(); i++) {
                String columnName = sheetTitles.get(i);
                HSSFCell cell = row.getCell(i);
                if (columnName != null && cell != null) {
                    valueSetter(datum, classInfo.getFieldMap().get(columnName), classInfo.getAnnoMap().get(columnName), cell);
                }
            }
            data.add(datum);
            index++;
        }
        return data;
    }

    private static <T> void valueSetter(T datum, Field field, ExcelColumn columnAnno, HSSFCell cell) throws IllegalAccessException {
        field.setAccessible(true);
        String jsonValue = null;
        switch (columnAnno.valueType()) {
            case STRING:
                if (CellType.STRING.equals(cell.getCellType())) {
                    jsonValue = "\"" + cell.getStringCellValue() + "\"";
                }
                break;
            case NUMERIC:
                if (CellType.NUMERIC.equals(cell.getCellType())) {
                    jsonValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case MAP:
                if (CellType.STRING.equals(cell.getCellType())) {
                    if (!StringUtils.isEmpty(columnAnno.mapName())) {
                        String inValue = ExcelValueMap.getInValue(columnAnno.mapName(), cell.getStringCellValue());
                        if (inValue != null) {
                            jsonValue = "\"" + inValue + "\"";
                        }
                    }
                }
                break;
            case JSON:
                if (CellType.STRING.equals(cell.getCellType())) {
                    jsonValue = cell.getStringCellValue();
                }
                break;
            default:
        }
        Object value = JSON.parseObject(jsonValue, field.getType());
        field.set(datum, value);
    }


    public static <T> HSSFWorkbook createSheet(HSSFWorkbook workbook, List<T> data, Class<T> clazz) throws IllegalAccessException {
        ObjectExcelInfo classInfo = new ObjectExcelInfo(clazz);
        if (!classInfo.isExcelEntity()) {
            //未添加注解
            return workbook;
        }

        //创建工作表对象
        HSSFSheet sheet = workbook.createSheet(classInfo.getSheetName());//设置sheet的Name
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
                cellSetter(cell, classInfo.getFieldMap().get(columnName), classInfo.getAnnoMap().get(columnName), datum);
            }
            index++;
        }
        return workbook;
    }

    private static <T> HSSFCell cellSetter(HSSFCell cell, Field field, ExcelColumn columnAnno, T datum) throws IllegalAccessException {
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
                case NUMERIC:
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(Double.parseDouble(String.valueOf(value)));
                    break;
                case MAP:
                    if (!StringUtils.isEmpty(columnAnno.mapName())) {
                        String outValue = ExcelValueMap.getOutValue(columnAnno.mapName(), String.valueOf(value));
                        if (outValue != null) {
                            value = outValue;
                        }
                    }
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(String.valueOf(value));
                    break;
                case JSON:
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(JSON.toJSONString(value));
                    break;
                default:
                    cell.setCellType(CellType.BLANK);
            }
        }
        return cell;
    }
}
