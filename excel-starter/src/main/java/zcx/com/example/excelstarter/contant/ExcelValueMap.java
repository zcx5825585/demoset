package zcx.com.example.excelstarter.contant;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段转换使用的map
 *
 * @author DELL
 * @date 2020/11/3
 */
public class ExcelValueMap {
    public static Map<String, ExcelValueMap> excelValueMaps = new HashMap<>();

    private Map<String, String> inMap = new HashMap();
    private Map<String, String> outMap = new HashMap();


    public static Map<String, String> getInMap(String mapName) {
        ExcelValueMap excelValueMap=excelValueMaps.get(mapName);
        if (excelValueMap == null) {
            return null;
        }
        return excelValueMap.inMap;
    }

    public static String getInValue(String mapName, String outValue) {
        Map<String, String> inMap = getInMap(mapName);
        if (inMap != null) {
            return inMap.get(outValue);
        }
        return null;
    }
    public static Map<String, String> getOutMap(String mapName) {
        ExcelValueMap excelValueMap=excelValueMaps.get(mapName);
        if (excelValueMap == null) {
            return null;
        }
        return excelValueMap.outMap;
    }

    public static String getOutValue(String mapName, String inValue) {
        Map<String, String> outMap = getOutMap(mapName);
        if (outMap != null) {
            return outMap.get(inValue);
        }
        return null;
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
}
