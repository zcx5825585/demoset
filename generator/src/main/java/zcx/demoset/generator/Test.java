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
                new Sheet("broken_log",
                        "报废记录",
                        "broken_log_id\tID\tbigint\t\t是\t是\t\n" +
                                "vaccines_id\t操作疫苗ID\tbigint\t\t\t\t具体一支疫苗\n" +
                                "des_code\t药品电子监管码\tvarchar\t20\t\t\t\n" +
                                "vaccines_name\t疫苗名称\tvarchar\t50\t\t\t\n" +
                                "quantity\t包装规格(此次操作)\tbigint\t\t\t\t操作几支 -1 为全部\n" +
                                "device_id\t设备编码\tvarchar\t18\t\t\t\n" +
                                "handle_by\t操作人\tvarchar\t50\t\t\t\n" +
                                "status\t操作结果\tvarchar\t10\t\t\t成功失败\n" +
                                "broken_reason\t报废原因\tvarchar\t150\t\t\t\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );
        sheets.add(
                new Sheet("vaccines_catalog",
                        "疫苗类别",
                        "catalog_id\tID\tbigint\t\t是\t是\t\n" +
                                "catalog_code\t制品编码\tvarchar\t50\t\t\t\n" +
                                "catalog_name\t制品名称\tvarchar\t50\t\t\t部门表中选取的ID\n" +
                                "abbreviation\t制品简称\tvarchar\t50\t\t\t\n" +
                                "abbreviation_en\t英文简称\tvarchar\t50\t\t\t\n" +
                                "type_abbreviation\t种类中文简称\tvarchar\t50\t\t\t\n" +
                                "type_abbreviation_en\t种类英文简称\tvarchar\t50\t\t\t\n" +
                                "catalog_price\t制品价格\tvarchar\t50\t\t\t\n" +
                                "catalog_type\t疫苗类型\tvarchar\t50\t\t\t\n" +
                                "vaccines_level\t疫苗大类\tvarchar\t50\t\t\t一类 或 二类\n" +
                                "del_flag\t删除标志\tchar\t1\t\t\t0代表可用 2代表废弃\n" +
                                "abandonment_reason\t废弃原因\tvarchar\t255\t\t\t\n" +
                                "level\t类别等级\tvarchar\t8\t\t\t1 一级 2二级 3三级\n" +
                                "injection_start_age\t接种起始年龄\tvarchar\t100\t\t\t\n" +
                                "injection_method\t接种部位/途径\tvarchar\t100\t\t\t\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );
        sheets.add(
                new Sheet("vaccines_manufacturer",
                        "疫苗生产厂家",
                        "manufacturer_id\tID\tbigint\t\t是\t是\t\n" +
                                "manufacturer_name\t生产厂家\tvarchar\t50\t\t\t\n" +
                                "manufacturer_code\t厂家编号\tvarchar\t50\t\t\t\n" +
                                "manufacturer_nation\t生产厂家所在国\tvarchar\t50\t\t\t\n" +
                                "manufacturer_address\t厂家地址\tvarchar\t255\t\t\t厂家地址\n" +
                                "manufacturer_telephone\t联系方式\tvarchar\t50\t\t\t电话\n" +
                                "del_flag\t删除标志\tchar\t1\t\t\t0代表存在 2代表删除\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );

        sheets.add(
                new Sheet("vaccines",
                        "疫苗信息(每支)",
                        "vaccines_id\tID\tbigint\t\t是\t是\t\n" +
                                "catalog_id\t类别id\tbigint\t\t\t\t类别Id\n" +
                                "manufacturer_name\t生产厂家\tvarchar\t50\t\t\t\n" +
                                "device_id\t所在设备编码\tvarchar\t18\t\t\t外部为-1\n" +
                                "device_type\t设备类型\tvarchar\t10\t\t\t\n" +
                                "drawer_id\t疫苗所在冷藏室序号\tvarchar\t18\t\t\t序号从1 开始\n" +
                                "batch_num\t生产批号\tvarchar\t50\t\t\t\n" +
                                "des_code\t药品电子监管码\tvarchar\t20\t\t\t\n" +
                                "vaccines_name\t疫苗名称\tvarchar\t50\t\t\t\n" +
                                "quantity\t包装规格\tbigint\t\t\t\t(一盒几支)\n" +
                                "remain_quantity\t包装规格(剩余)\tbigint\t\t\t\t(剩余几支)\n" +
                                "unit\t疫苗单位\tvarchar\t20\t\t\t(支,个)\n" +
                                "spec\t疫苗规格\tvarchar\t20\t\t\t(每支多少ml/mg)\n" +
                                "dosage_form\t剂型\tvarchar\t20\t\t\t\n" +
                                "dosage_form_spec\t剂型规格\tvarchar\t50\t\t\t\n" +
                                "storage_conditions\t保存条件\tvarchar\t30\t\t\t\n" +
                                "dose\t剂量\tvarchar\t50\t\t\t每次注射剂量ml()\n" +
                                "total_dose\t单支可使用剂次\tbigint\t\t\t\t剂次\n" +
                                "remain_dose\t剩余剂次\tbigint\t\t\t\t剂次\n" +
                                "production_date\t生产日期\tdatetime\t\t\t\t\n" +
                                "valid_duration\t疫苗有效期\tbigint\t\t\t\t多少天 (一个月按30天机算)\n" +
                                "valid_date\t有效期截止日期\tdatetime\t\t\t\t每天\n" +
                                "drug_approval_number\t药品批准文号\tvarchar\t50\t\t\t\n" +
                                "drug_approval_number_end_date\t药品批准文号有效期\tdatetime\t\t\t\t有效期结束日期\n" +
                                "catalog_nation\t生产国\tvarchar\t50\t\t\t\n" +
                                "is_foreign\t是否进口\tvarchar\t10\t\t\t1 国产 2 进口\n" +
                                "status\t状态\tvarchar\t10\t\t\t0未入库 1正常 2用完 3报废 4冻结\n" +
                                "broken_reason\t报废原因\tvarchar\t150\t\t\t\n" +
                                "dep_id\t部门ID\tbigint\t\t\t\t所属医院\n" +
                                "del_flag\t删除标志\tchar\t1\t\t\t0代表存在 2代表删除\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );
        sheets.add(
                new Sheet("transport_bill",
                        "出入库单",
                        "bill_id\tID\tbigint\t\t是\t是\t\n" +
                                "bill_no\t单号\tvarchar\t50\t\t\t\n" +
                                "handle_by\t操作人\tvarchar\t50\t\t\t\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );
        sheets.add(
                new Sheet("transport_info",
                        "出入库详细记录",
                        "transport_info_id\tID\tbigint\t\t是\t是\t\n" +
                                "transport_form_id\t出入库单ID\tbigint\t\t\t\t\n" +
                                "operate\t类型\tvarchar\t64\t\t\t出库 入库 转移\n" +
                                "catalog_id\t疫苗类别ID\tbigint\t\t\t\t\n" +
                                "catalog_name\t制品名称\tvarchar\t50\t\t\t部门表中选取的ID\n" +
                                "vaccines_id\t操作疫苗ID\tbigint\t\t\t\t具体一支疫苗\n" +
                                "vaccines_name\t疫苗名称\tvarchar\t50\t\t\t\n" +
                                "des_code\t药品电子监管码\tvarchar\t20\t\t\t\n" +
                                "quantity\t包装规格(此次操作)\tbigint\t\t\t\t操作几支 -1 为全部\n" +
                                "device_id\t设备编码\tvarchar\t18\t\t\t外部为-1\n" +
                                "drawer_id\t疫苗所在冷藏室序号\tvarchar\t18\t\t\t序号从1 开始\n" +
                                "status\t操作结果\tvarchar\t10\t\t\t 0未完成 1完成 2失败\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );
        sheets.add(
                new Sheet("device_site",
                        "接种点信息",
                        "site_id\tID\tbigint\t\t是\t是\t\n" +
                                "site_name\t接种点名称\tvarchar\t50\t\t\t\n" +
                                "site_code\t接种点编号\tvarchar\t50\t\t\t\n" +
                                "site_mobil\t联系方式\tvarchar\t50\t\t\t\n" +
                                "site_address\t接种点地址\tvarchar\t50\t\t\t\n" +
                                "province\t省\tvarchar\t50\t\t\t\n" +
                                "city\t市\tvarchar\t50\t\t\t\n" +
                                "area\t县(区)\tvarchar\t50\t\t\t\n" +
                                "lng\t经度\tfloat\t\t\t\t\n" +
                                "lat\t纬度\tfloat\t\t\t\t\n" +
                                "dep_id\t部门ID\tbigint\t\t\t\t所属医院\n" +
                                "del_flag\t删除标志\tchar\t1\t\t\t0代表存在 2代表删除\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );
        sheets.add(
                new Sheet("device",
                        "中心库(接种台)信息",
                        "id\tID\tbigint\t\t是\t是\t\n" +
                                "site_id\t接种点ID\tbigint\t\t\t\t   -1 为不属于任何接种点\n" +
                                "device_name\t设备名称\tvarchar\t64\t\t\t\n" +
                                "device_type\t设备类型\tvarchar\t10\t\t\t 中心库 接种台\n" +
                                "device_id\t设备编码\tvarchar\t18\t\t\t\n" +
                                "warning_statue\t报警状态\tvarchar\t10\t\t\t报警 正常\n" +
                                "warning_count\t报警数\tbigint\t\t\t\t\n" +
                                "max_temp\t冷藏最高温(含)\tfloat\t\t\t\t只能修改单个设备的\n" +
                                "min_temp\t冷藏最低温(含)\tfloat\t\t\t\t只能修改单个设备的\n" +
                                "clod_max_temp\t冷冻最高温(含)\tfloat\t\t\t\t只能修改单个设备的\n" +
                                "clod_min_temp\t冷冻最低温(含)\tfloat\t\t\t\t只能修改单个设备的\n" +
                                "dep_id\t部门ID\tbigint\t\t\t\t所属医院\n" +
                                "del_flag\t删除标志\tchar\t1\t\t\t0代表存在 2代表删除\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );

        sheets.add(
                new Sheet("device_box",
                        "冷藏室信息",
                        "box_id\tID\tbigint\t\t是\t是\t\n" +
                                "device_id\t设备编码\tvarchar\t18\t\t\t\n" +
                                "drawer_id\t疫苗所在冷藏室序号\tvarchar\t18\t\t\t\n" +
                                "box_name\t冷藏室名称\tvarchar\t64\t\t\t\n" +
                                "del_flag\t删除标志\tchar\t1\t\t\t0代表存在 2代表删除\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );

        sheets.add(
                new Sheet("device_log",
                        "设备记录",
                        "device_id\t设备编码\tvarchar\t18\t\t\t\n" +
                                "field\t属性\tvarchar\t50\t\t\t\n" +
                                "type\t属性类型\tvarchar\t10\t\t\t\n" +
                                "value\t属性值\tvarchar\t255\t\t\t\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );



        sheets.add(
                new Sheet("injection_log",
                        "接种记录",
                        "injection_log_id\tID\tbigint\t\t是\t是\t\n" +
                                "catalog_code\t制品编码\tvarchar\t50\t\t\t\n" +
                                "catalog_name\t制品名称\tvarchar\t50\t\t\t\n" +
                                "vaccines_id\t操作疫苗ID\tbigint\t\t\t\t具体一支疫苗\n" +
                                "des_code\t药品电子监管码\tvarchar\t20\t\t\t\n" +
                                "device_id\t设备编码\tvarchar\t18\t\t\t\n" +
                                "use_dose\t消耗剂次\tbigint\t\t\t\t总是1次\n" +
                                "position\t注射部位\tvarchar\t50\t\t\t\n" +
                                "certificate_no\t接种证号\tvarchar\t50\t\t\t\n" +
                                "ic_code\tIC卡号\tvarchar\t50\t\t\t\n" +
                                "handle_by\t操作人\tvarchar\t50\t\t\t\n" +
                                "status\t疫苗接种的标识\tvarchar\t10\t\t\t1已接种2取消接种\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );
        sheets.add(
                new Sheet("warning_log",
                        "报警记录",
                        "warning_log_id\tID\tbigint\t\t是\t是\t\n" +
                                "device_id\t设备ID\tbigint\t\t\t\t\n" +
                                "warning_type\t警报类别\tvarchar\t10\t\t\t\n" +
                                "warning_status\t警报状态\tvarchar\t10\t\t\t报警 已消警\n" +
                                "message\t报警消息\ttext\t\t\t\t\n" +
                                "category_id\t疫苗类别ID\tbigint\t\t\t\t疫苗报警时\n" +
                                "warning_temp\t报警时温度\tfloat\t\t\t\t温度报警时\n" +
                                "handle_by\t操作人\tvarchar\t50\t\t\t\n" +
                                "handler_message\t消警备注\tvarchar\t255\t\t\t\n" +
                                "remark\t备注\tvarchar\t255\t\t\t\n" +
                                "create_by\t创建者\tvarchar\t50\t\t\t\n" +
                                "create_time\t创建时间\tdatetime\t\t\t\t\n" +
                                "update_by\t更新者\tvarchar\t50\t\t\t\n" +
                                "update_time\t更新时间\tdatetime\t\t\t\t\n")
        );


        return sheets;
    }
}
