package zcx.com.example.excelstarter.generator;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import zcx.com.example.excelstarter.anno.ExcelColumn;
import zcx.com.example.excelstarter.contant.ExcelValueMap;
import zcx.com.example.excelstarter.entity.ObjectExcelInfo;

import java.lang.reflect.Field;
import java.util.List;

/**
 * csv生成器
 *
 * @author zcx
 * @date 2020/11/03
 */
public class CsvGenerator {

    public static <T> String createCsv(List<T> data, Class<T> clazz) throws IllegalAccessException {
        ObjectExcelInfo classInfo = new ObjectExcelInfo(clazz);
        if (!classInfo.isExcelEntity()){
            //未添加注解
            return null;
        }

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
        String stringValue;
        Object value = field.get(datum);
        //设置默认值
        //value为空 并且设置了默认值 将value修改为默认值
        if (value == null && !"".equals(columnAnno.defaultValue())) {
            value = columnAnno.defaultValue();
        }
        //设置默认值后再次校验value是否为空 依然为空则将stringValue设为空字符串，跳过转换
        if (value == null) {
            stringValue = "";
        } else {//不为空则进行相应转换
            switch (columnAnno.valueType()) {
                case JSON:
                    stringValue = JSON.toJSONString(value);
                    break;
                case MAP:
                    if (!StringUtils.isEmpty(columnAnno.mapName())) {
                        String outValue = ExcelValueMap.getOutValue(columnAnno.mapName(), String.valueOf(value));
                        if (outValue != null) {
                            value = outValue;
                        }
                    }
                    stringValue = String.valueOf(value);
                    break;
                case NUMERIC:
                case STRING:
                default:
                    stringValue = String.valueOf(value);
            }
        }
        return stringValue;
    }
}
