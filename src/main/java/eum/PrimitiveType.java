package eum;

public enum PrimitiveType {

    PT_VOID("void"),
    PT_BOOLEAN("boolean"),
    PT_BYTE("byte"),
    PT_CHAR("char"),
    PT_SHORT("short"),
    PT_INT("int"),
    PT_LONG("long"),
    PT_FLOAT("float"),
    PT_DOUBLE("double");


    private String type;

    PrimitiveType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static PrimitiveType getByType(String type) {
        for(PrimitiveType primitiveType : values()) {
            if(primitiveType.type.equals(type)) {
                return primitiveType;
            }
        }
        return null;
    }
}

