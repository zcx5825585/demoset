package zcx.com.example.excel.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zcx.com.example.excel.entity.ExcelEntity;
import zcx.com.example.excel.repository.ExcelRepository;
import zcx.com.example.excel.util.excel.CsvGenerator;
import zcx.com.example.excel.util.excel.ExcelGenerator;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ExcelService {

    @Resource
    private ExcelRepository excelRepository;

    public void readWorkBook(HSSFWorkbook workbook){
        try {
            List<ExcelEntity> excelEntities=ExcelGenerator.mapSheet(workbook,ExcelEntity.class);
            for (ExcelEntity excelEntity : excelEntities) {
                System.out.println(excelEntity.getName());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public HSSFWorkbook getWorkBook() {
        List<ExcelEntity> excelEntities = excelRepository.findAll();
        HSSFWorkbook workbook = null;
        try {

            //创建工作薄对象
            workbook = new HSSFWorkbook();
            //生成sheet
            workbook = ExcelGenerator.createSheet(workbook, excelEntities, ExcelEntity.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return workbook;
    }

    public String getCsv() {
        List<ExcelEntity> excelEntities = excelRepository.findAll();
        String csv = null;
        try {
            csv = CsvGenerator.createCsv(excelEntities, ExcelEntity.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return csv;
    }
}
