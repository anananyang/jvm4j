package emu;

import classFile.constants.*;

import java.util.EnumMap;

public enum ConstantType {

    CONSTANT_Utf8((byte) 1),
    CONSTANT_Integer((byte) 3),
    CONSTANT_Float((byte) 4),
    CONSTANT_Long((byte) 5),
    CONSTANT_Double((byte) 6),
    CONSTANT_Class((byte) 7),
    CONSTANT_String((byte) 8),
    CONSTANT_Fieldref((byte) 9),
    CONSTANT_Methodref((byte) 10),
    CONSTANT_InterfaceMethodref((byte) 11),
    CONSTANT_NameAndType((byte) 12),
    CONSTANT_MethodHandle((byte) 15),
    CONSTANT_MethodType((byte) 16),
    CONSTANT_InvokeDynamic((byte) 18);

    private byte tag;

    ConstantType(byte tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public static ConstantType getByTypeValue(byte tag) {
        for (ConstantType constantType : values()) {
            if (constantType.getTag() == tag) {
                return constantType;
            }
        }
        return null;
    }

}
