package eum;

public enum ArrayType {
    AT_BOOLEAN(4, "[Z", "boolean"),
    AT_CHAR(5, "[C", "char"),
    AT_FLOAT(6, "[F", "float"),
    AT_DOUBLE(7, "[D", "double"),
    AT_BYTE(8, "[B", "byte"),
    AT_SHORT(9, "[S", "short"),
    AT_INT(10, "[I", "int"),
    AT_LONG(11, "[J", "long");

    private int typeVal;
    private String typeDesc;
    private String type;


    ArrayType(int typeVal, String typeDesc, String type) {
        this.typeVal = typeVal;
        this.typeDesc = typeDesc;
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public static ArrayType getByTypeVal(int typeVal) {
        for (ArrayType arrayType : values()) {
            if (typeVal == arrayType.typeVal) {
                return arrayType;
            }
        }
        return null;
    }

    public static String getTypeDescByType(String type) {
        ArrayType arrayType = getByType(type);
        return arrayType == null ? null : arrayType.typeDesc;
    }

    public static ArrayType getByType(String type) {
        for (ArrayType arrayType : values()) {
            if (type.equals(arrayType.type)) {
                return arrayType;
            }
        }
        return null;
    }

    public static String getTypeByTypeDesc(String typeDesc) {
        ArrayType arrayType = getByTypeDesc(typeDesc);
        return arrayType == null ? null : arrayType.type;
    }

    public static ArrayType getByTypeDesc(String typeDesc) {
        for (ArrayType arrayType : values()) {
            if (typeDesc.equals(arrayType.typeDesc)) {
                return arrayType;
            }
        }
        return null;
    }

}
