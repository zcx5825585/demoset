package zcx.com.example.excelstarter.contant;

/**
 * 字段类型
 *
 * @author zcx
 * @date 2020/11/3
 */
public enum FiledType {

    NUMERIC(0),
    STRING(1),
    MAP(2),
    JSON(3);

    private final int code;

    private FiledType(int code) {
        this.code = code;
    }

    public static FiledType forInt(int code) {
        FiledType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            FiledType type = var1[var3];
            if (type.code == code) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid CellType code: " + code);
    }

    public int getCode() {
        return this.code;
    }
}
