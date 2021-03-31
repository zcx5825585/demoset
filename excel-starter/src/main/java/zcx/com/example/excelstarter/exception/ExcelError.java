package zcx.com.example.excelstarter.exception;

public class ExcelError {

    private String errorInfo;

    private String errorSheet;

    private Integer errorRowNo;

    private Integer errorCellNo;


    public ExcelError(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public ExcelError(String errorInfo,String errorSheet ) {
        this.errorSheet = errorSheet;
        this.errorInfo = errorInfo;
    }

    public ExcelError(String errorInfo , String errorSheet,Integer errorRowNo ) {
        this.errorSheet = errorSheet;
        this.errorRowNo = errorRowNo;
        this.errorInfo = errorInfo;
    }

    public ExcelError(String errorInfo, String errorSheet, Integer errorRowNo, Integer errorCellNo) {
        this.errorInfo = errorInfo;
        this.errorSheet = errorSheet;
        this.errorRowNo = errorRowNo;
        this.errorCellNo = errorCellNo;
    }
}
