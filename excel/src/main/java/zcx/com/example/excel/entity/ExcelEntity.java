package zcx.com.example.excel.entity;

import zcx.com.example.excelstarter.anno.ExcelColumn;
import zcx.com.example.excelstarter.anno.ExcelSheet;
import zcx.com.example.excelstarter.contant.FiledType;

import javax.persistence.*;

@Entity
@Table(name = "car_device")
@ExcelSheet(sheetName = "车辆设备")
public class ExcelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ExcelColumn(columnName = "设备识别码", valueType = FiledType.STRING)
    private String deviceNo;
    @ExcelColumn(columnName = "警报状态", valueType = FiledType.MAP, mapName = "device-status")
    private String warningStatus;
    @ExcelColumn(columnName = "警报数",valueType = FiledType.NUMERIC,defaultValue = "-")
    private Integer warningCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(String warningStatus) {
        this.warningStatus = warningStatus;
    }

    public Integer getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(Integer warningCount) {
        this.warningCount = warningCount;
    }
}
