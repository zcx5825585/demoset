package zcx.com.example.excel.service;

import org.springframework.stereotype.Component;
import zcx.com.example.excelstarter.valueMap.ExcelValueMap;
import zcx.com.example.excelstarter.valueMap.MapLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * excel映射初始化
 *
 * @author DELL
 * @date 2020/11/9
 */
@Component
public class MyExcelMapLoader extends MapLoader {

    @Override
    public void loadExcelMap() {
        Map<String, String> cityCodeMap = new HashMap<>();
        cityCodeMap.put("0", "未报警");
        cityCodeMap.put("1", "警报中");
        ExcelValueMap.putMap("device-status", cityCodeMap);
    }
}
