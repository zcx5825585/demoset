package zcx.com.example.excel.util.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectExcelInfo {
    private String sheetName;
    private boolean isExcelEntity = true;
    //表头列表 也是keySet
    private List<String> titles;
    //属性map 用于取出数据
    private Map<String, Field> fieldMap;
    //注解map 用于对数据进行加工
    private Map<String, ExcelColumn> annoMap;

    public ObjectExcelInfo(Class clazz) {
        ExcelSheet sheetAnno = (ExcelSheet) clazz.getAnnotation(ExcelSheet.class);
        if (sheetAnno == null) {
            this.isExcelEntity = false;
            return;
        }
        this.sheetName = "".equals(sheetAnno.sheetName()) ? clazz.getSimpleName() : sheetAnno.sheetName();

        this.titles = new ArrayList<>();
        this.fieldMap = new HashMap<>();
        this.annoMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            ExcelColumn columnAnno = field.getAnnotation(ExcelColumn.class);
            if (columnAnno != null) {
                String columnName = "".equals(columnAnno.columnName()) ? field.getName() : columnAnno.columnName();
                titles.add(columnName);
                fieldMap.put(columnName, field);
                annoMap.put(columnName, columnAnno);
            }
        }
    }

    public boolean isExcelEntity() {
        return this.isExcelEntity;
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<String> getTitles() {
        return titles;
    }

    public Map<String, Field> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Field> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Map<String, ExcelColumn> getAnnoMap() {
        return annoMap;
    }

    public void setAnnoMap(Map<String, ExcelColumn> annoMap) {
        this.annoMap = annoMap;
    }
}
