package zcx.com.example.excel.init;

import org.springframework.stereotype.Component;
import zcx.com.example.excel.util.excel.ExcelValueMap;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * excel映射初始化
 *
 * @author DELL
 * @date 2020/11/9
 */
@Component
public class ExcelMapLoader {

    @PostConstruct
    public void loadExcelMap() {
        Map<String, String> cityCodeMap = new HashMap<>();
        cityCodeMap.put("0531", "济南");
        cityCodeMap.put("0532", "青岛");
        ExcelValueMap.putMap("city-code", cityCodeMap);
    }
}
