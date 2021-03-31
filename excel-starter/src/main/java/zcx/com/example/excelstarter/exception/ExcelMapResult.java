package zcx.com.example.excelstarter.exception;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class ExcelMapResult<T> {

    private List<ExcelError> errorList;

    private List<T> data;

    public void addError(ExcelError error) {
        if (errorList == null) {
            errorList = new ArrayList<>();
        }
        errorList.add(error);
    }

    public void get() {
        String jsonErrors = JSON.toJSONString(errorList);
    }

    public List<ExcelError> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ExcelError> errorList) {
        this.errorList = errorList;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
