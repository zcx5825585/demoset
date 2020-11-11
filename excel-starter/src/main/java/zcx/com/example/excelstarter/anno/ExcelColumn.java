package zcx.com.example.excelstarter.anno;


import zcx.com.example.excelstarter.contant.FiledType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解
 *
 * @author zcx
 * @date 2020/11/3
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String columnName() default "";

    String defaultValue() default "";

    FiledType valueType() default FiledType.STRING;

    String mapName() default "";
}
