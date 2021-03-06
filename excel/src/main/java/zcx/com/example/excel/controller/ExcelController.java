package zcx.com.example.excel.controller;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zcx.com.example.excel.service.ExcelService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@RestController
@RequestMapping
public class ExcelController {

    @Resource
    private ExcelService excelService;


    @GetMapping("csv")
    public void getCsv(HttpServletResponse response) throws IOException {
        String csv = excelService.getCsv();
        if (csv == null) {
            csv = "";
        }
        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=test.csv");//默认Excel名称

        PrintWriter writer = response.getWriter();
        writer.write(csv);
        writer.flush();
        writer.close();
    }

    @PostMapping
    public void readExcel(@RequestParam("file") MultipartFile file) throws IOException {
        HSSFWorkbook workbook=new HSSFWorkbook(file.getInputStream());
        excelService.readWorkBook(workbook);
    }

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
