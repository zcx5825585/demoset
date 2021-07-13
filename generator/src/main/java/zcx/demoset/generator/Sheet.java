package zcx.demoset.generator;

public class Sheet {
    private String tableName;
    private String tableRealName;
    private String content;

    public Sheet() {
    }

    public Sheet(String tableName, String tableRealName, String content) {
        this.content = content;
        this.tableName = tableName;
        this.tableRealName = tableRealName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableRealName() {
        return tableRealName;
    }

    public void setTableRealName(String tableRealName) {
        this.tableRealName = tableRealName;
    }
}
