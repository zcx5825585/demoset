package zcx.com.example.excel.entity;

import zcx.com.example.excel.util.excel.ExcelColumn;
import zcx.com.example.excel.util.excel.ExcelSheet;
import zcx.com.example.excel.util.excel.FiledType;

import javax.persistence.*;

@Entity
@Table(name = "excel")
@ExcelSheet(sheetName = "城市")
public class ExcelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ExcelColumn(columnName = "城市", valueType = FiledType.STRING)
    private String name;
    @ExcelColumn(columnName = "区号", valueType = FiledType.MAP, mapName = "city-code")
    private String description;

    @ExcelColumn(defaultValue = "-")
    private String note;

    @ExcelColumn(columnName = "数字",valueType = FiledType.NUMERIC,defaultValue = "-")
    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
