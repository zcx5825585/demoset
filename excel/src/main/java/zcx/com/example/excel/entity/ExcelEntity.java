package zcx.com.example.excel.entity;

import zcx.com.example.excel.util.excel.ExcelColumn;
import zcx.com.example.excel.util.excel.ExcelSheet;

import javax.persistence.*;

@Entity
@Table(name = "excel")
@ExcelSheet(sheetName = "城市")
public class ExcelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ExcelColumn(columnName = "城市")
    private String name;
    @ExcelColumn(columnName = "区号")
    private String description;

    @ExcelColumn(defaultValue = "-")
    private String note;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
