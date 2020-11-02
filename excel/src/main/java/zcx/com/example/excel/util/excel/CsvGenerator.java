package zcx.com.example.excel.util.excel;

import java.lang.reflect.Field;
import java.util.List;

public class CsvGenerator {


    public static <T> String createCsv(List<T> data, Class<T> clazz) throws IllegalAccessException {
        ObjectExcelInfo classInfo = new ObjectExcelInfo(clazz);

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
