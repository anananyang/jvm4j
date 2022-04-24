package emu;

/**
 * 虚拟机规范规定了23种属性，先解析其中的 8 种
 */
public enum AttributeType {
    Code,
    ConstantValue,
    Deprecated,
    Exceptions,
    LineNumberTable,
    LocalVariableTable,
    SourceFile,
    Synthetic,
    Unparse;   // 这里不包含所有的属性的，所有对于未解析的属性使用 Unparse 进行标记


    public static boolean contain(String type) {
        for (AttributeType attributeType : values()) {
            if (attributeType.name().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
