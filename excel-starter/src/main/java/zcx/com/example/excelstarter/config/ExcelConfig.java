package zcx.com.example.excelstarter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zcx
 * @date 2020/11/11
 */
@Configuration
@EnableConfigurationProperties(ExcelProperties.class)
@ConditionalOnProperty(
        prefix = "zcx-excel",
        name = "isopen",
        havingValue = "true"
)
public class ExcelConfig {
    @Autowired
    private ExcelProperties excelProperties;
}

