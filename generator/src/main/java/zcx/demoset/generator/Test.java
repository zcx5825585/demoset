package zcx.demoset.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {

        String path="C:\\Users\\zcx\\Desktop";
        File sqlFile=new File(path+"\\generator.sql");
        if (!sqlFile.exists()){
            sqlFile.createNewFile();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SET NAMES utf8mb4;\n" +
                "SET FOREIGN_KEY_CHECKS = 0;\n\n\n\n");

        List<Sheet> sheets = getSheets();
        for (Sheet sheet : sheets) {
            sql.append(getSheetCreateSql(sheet));
        }


        sql.append("SET FOREIGN_KEY_CHECKS = 1;");

        FileWriter fos = new FileWriter(sqlFile, true);
        fos.write(sql.toString());
        fos.flush();
        fos.close();
    }

    public static StringBuilder getSheetCreateSql(Sheet sheet) {
        StringBuilder tableSql = new StringBuilder();
        //header
        tableSql.append(String.format(
                "DROP TABLE IF EXISTS `%s`;\n" +
                        "CREATE TABLE `%s`  (\n", sheet.getTableName(), sheet.getTableName()));

        //具体字段
        String[] rows = sheet.getContent().split("\n");
        String key="id";
        for (String row : rows) {
            Column column = new Column(row);
            key=column.getKey(key);
            tableSql.append(column.toCreateSql());
            //params 格式
            //0     1       2   3   4       5   6
            //字段名	中文名	类型	长度	非空字段	主键	备注
//            String[] params=row.split("\t",-1);
//            if ("id".equals(params[0])){
//                tableSql.append("`id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',\n");
//                continue;
//            }
//            if ("varchar".equals(params[2])){
//                params[2]=params[2]+"("+params[3]+")";
//            }
//            if (!StringUtils.isEmpty(params[6])){
//                params[1]=params[1]+"   "+params[6];
//            }
//            tableSql.append(String.format("`%s` %s NULL DEFAULT NULL COMMENT '%s',\n",params[0],params[2],params[1]));
        }

        //tail
        tableSql.append(String.format("PRIMARY KEY (`%s`) USING BTREE\n" +
                ") ENGINE = InnoDB AUTO_INCREMENT = 0 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '%s' ROW_FORMAT = Dynamic;\n" +
                "\n", key,sheet.getTableRealName()));
        tableSql.append("\n\n\n");
        return tableSql;
    }

    private static List<Sheet> getSheets() {
        List<Sheet> sheets = new ArrayList<>();
        sheets.add(
                new Sheet("transport_analy",
                        "出入库单明细",
                        "transport_info_id\tID\tbigint\t\t是\t是\t\n" +
                                "hospital_code\t医院编码\tvarchar\t50\t\t\t\n" +
                                "transport_bill_no\t出入库单ID\tvarchar\t50\t\t\t\n" +
                                "catalog_code\t类别编码\tvarchar\t50\t\t\t\n" +
                                "catalog_name\t类别名称\tvarchar\t50\t\t\t\n" +
                                "manufacturer_code\t厂家编码\tvarchar\t50\t\t\t\n" +
                                "manufacturer_name\t生产厂家\tvarchar\t50\t\t\t\n" +
                                "vaccines_name\t疫苗名称\tvarchar\t50\t\t\t\n" +
                                "batch_num\t生产批号\tvarchar\t50\t\t\t\n" +
                                "man_date\t生产日期\tvarchar\t50\t\t\t\n" +
                                "valid_date\t有效期截止日期\tvarchar\t50\t\t\t\n" +
                                "spec\t疫苗规格\tvarchar\t20\t\t\t\n" +
                                "quantity\t包装规格\tbigint\t\t\t\t\n" +
                                "count\t数量\tbigint\t\t\t\t\n" +
                                "remain_count\t未入库数量\tbigint\t\t\t\t\n" +
                                "status\t状态\tvarchar\t10\t\t\t0未开始 1未完成 2已完成\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );
        return sheets;
    }
}
