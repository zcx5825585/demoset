package zcx.com.example.excel.util.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassInfo {
    //表头列表 也是keySet
    private List<String> titles = new ArrayList<>();
    //属性map 用于取出数据
    private Map<String, Field> fieldMap = new HashMap<>();
    //注解map 用于对数据进行加工
    private Map<String, ExcelColumn> annoMap = new HashMap<>();

    public ClassInfo(Class clazz) {
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

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
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
