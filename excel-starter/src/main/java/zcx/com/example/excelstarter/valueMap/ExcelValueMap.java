package zcx.com.example.excelstarter.valueMap;

import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字段转换使用的map
 *
 * @author zcx
 * @date 2020/11/3
 */
public class ExcelValueMap {
    private static Map<String, ExcelValueMap> excelValueMaps = new HashMap<>();

    private Map<String, String> inMap = new HashMap();
    private Map<String, String> outMap = new HashMap();


    public static Map<String, String> getInMap(String mapName) {
        ExcelValueMap excelValueMap = excelValueMaps.get(mapName);
        if (excelValueMap == null) {
            return null;
        }
        return excelValueMap.inMap;
    }

    public static String getInValue(String mapName, String outValue) {
        Map<String, String> inMap = getInMap(mapName);
        if (inMap == null) {
            return null;
        }
        return inMap.get(outValue);
    }

    public static Map<String, String> getOutMap(String mapName) {
        ExcelValueMap excelValueMap = excelValueMaps.get(mapName);
        if (excelValueMap == null) {
            return null;
        }
        return excelValueMap.outMap;
    }

    public static String getOutValue(String mapName, String inValue) {
        Map<String, String> outMap = getOutMap(mapName);
        if (outMap == null) {
            return null;
        }
        return outMap.get(inValue);
    }

    public static void putMap(String mapName, Map<String, String> outMap) {
        ExcelValueMap excelValueMap = excelValueMaps.get(mapName);
        if (excelValueMap == null) {
            excelValueMap = new ExcelValueMap();
        }
        if (outMap == null) {
            outMap = new HashMap<>();
        }
        Map<String, String> inMap = new HashMap<>();
        outMap.forEach((k, v) -> inMap.put(v, k));
        excelValueMap.inMap = inMap;
        excelValueMap.outMap = outMap;
        excelValueMaps.put(mapName, excelValueMap);
    }

    public static <T> void putMap(List<T> list, String mapName, String inFiledName, String outFiledName) {
        Map<String, String> codeMap = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            Map beanMap = BeanMap.create(element);
            codeMap.put(String.valueOf(beanMap.get(inFiledName)), String.valueOf(beanMap.get(outFiledName)));
        }
        ExcelValueMap.putMap(mapName, codeMap);
    }
}
