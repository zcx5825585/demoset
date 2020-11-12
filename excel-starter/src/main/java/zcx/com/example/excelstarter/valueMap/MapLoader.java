package zcx.com.example.excelstarter.valueMap;

import javax.annotation.PostConstruct;

/**
 * map映射接口
 *
 * @author DELL
 * @date 2020/11/11
 */
public abstract class MapLoader {

    public abstract void loadExcelMap();

    @PostConstruct
    public void initMap(){
        loadExcelMap();
    }
}
