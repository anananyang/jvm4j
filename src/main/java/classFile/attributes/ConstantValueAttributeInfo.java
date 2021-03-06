package classFile.attributes;

import classFile.ConstantPool;
import classFile.reader.ByteReader;

public class ConstantValueAttributeInfo extends AttributeInfo {

    private int constantValueIndex;
    private ConstantPool constantPool;

    public ConstantValueAttributeInfo(String attrName,
                                      int attrLen,
                                      ByteReader byteReader,
                                      ConstantPool constantPool) {
        super(attrName, attrLen);
        this.constantPool = constantPool;
        this.constantValueIndex = byteReader.readUnit16();
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}
