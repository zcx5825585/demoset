package zcx.com.example.excel.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zcx.com.example.excel.entity.ExcelEntity;
import zcx.com.example.excel.repository.ExcelRepository;
import zcx.com.example.excelstarter.exception.ExcelError;
import zcx.com.example.excelstarter.exception.ExcelMapResult;
import zcx.com.example.excelstarter.generator.CsvGenerator;
import zcx.com.example.excelstarter.generator.ExcelGenerator;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ExcelService {

    @Resource
    private ExcelRepository excelRepository;

    public void readWorkBook(HSSFWorkbook workbook) {
        ExcelMapResult<ExcelEntity> excelMapResult = ExcelGenerator.mapSheet(workbook, ExcelEntity.class,a->{
            System.out.println(a.getDeviceNo());
            return null;
        });
        List<ExcelError> errorList = excelMapResult.getErrorList();
        if (errorList.size() > 0) {

        }
        List<ExcelEntity> excelEntities = excelMapResult.getData();
        for (ExcelEntity excelEntity : excelEntities) {
            System.out.println(excelEntity.getDeviceNo());
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
