package classFile.attributes;

import classFile.ConstantPool;
import classFile.reader.ByteReader;

/**
 * Deprecated属性用于指出类、接口、字段或者方法已经不建议使用。编译时，编译器可以根据 Deprecated 输出警告信息。
 * Deprecated 的属性长度为 0
 */
public class DeprecatedAttributeInfo extends AttributeInfo {

    public DeprecatedAttributeInfo(String attrName,
                                   int attrLen,
                                   ByteReader byteReader,
                                   ConstantPool constantPool) {
        super(attrName, attrLen);
    }
}
