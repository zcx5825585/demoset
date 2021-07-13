package zcx.demoset.generator;

public class Column {
    //字段名
    private String columnName;
    //中文名
    private String columnRealName;
    //类型
    private String type;
    //长度
    private String length;
    //非空字段
    private boolean isNull;
    //主键
    private String defaultValue;
    //备注
    private String remark;
    private boolean isKey=false;

    public Column() {
    }

    public Column(String excelRow) {
        String[] params = excelRow.split("\t", -1);
        //0     1       2   3   4       5   6
        //字段名	中文名	类型	长度	非空字段	主键	备注
        this.columnName = params[0];
        this.columnRealName = params[1];
        this.type = params[2];
        this.length = params[3];
        this.isNull = "是".equals(params[4]);
        if ("是".equals(params[5])) {
            this.defaultValue = "AUTO_INCREMENT";
            this.isKey=true;
        } else {
            this.defaultValue = "DEFAULT NULL";
        }
        this.remark = params[6];
    }

    public String getKey(String key){
        if (this.isKey) {
            return this.columnName;
        } else {
            return key;
        }
    }
    public String toCreateSql() {
        String columnName = this.columnName;
        String type = this.type;
        if (this.length == null || "".equals(this.length)) {

        } else {
            type = type + "(" + this.length + ")";
        }
        String remark = this.columnRealName;
        if (this.remark == null || "".equals(this.remark)) {

        } else {
            remark = remark + "    " + this.remark;
        }
        String isNull = "NULL";
        if (this.isNull) {
            isNull = "NOT " + isNull;
        }
        if (columnName.equals("del_flag")){
            this.defaultValue="DEFAULT '0'";
        }

        String defaultValue = this.defaultValue;

        return String.format("`%s` %s %s %s COMMENT '%s',\n", columnName, type, isNull, defaultValue, remark);
    }
}
