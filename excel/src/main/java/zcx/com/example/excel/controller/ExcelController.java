package zcx.com.example.excel.controller;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zcx.com.example.excel.service.ExcelService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping
public class ExcelController {

    @Resource
    private ExcelService excelService;

    @GetMapping
    public void getExcel(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = excelService.getWorkBook();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=excel.xlsx");//默认Excel名称

        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }
}
