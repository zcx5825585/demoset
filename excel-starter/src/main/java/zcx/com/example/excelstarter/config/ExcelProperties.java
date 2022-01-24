package zcx.com.example.excelstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zcx
 * @date 2020/11/11
 */
@ConfigurationProperties(prefix = "zcx-excel")
public class ExcelProperties  {
    private String testParam = "test";

    public String getTestParam() {
        return testParam;
    }

    public void setTestParam(String testParam) {
        this.testParam = testParam;
    }
}
