package classFile.attributes;

import classFile.ConstantPool;
import classFile.reader.ByteReader;

public class ExceptionsAttributeInfo extends AttributeInfo {

    private int[] exceptionIndexTable;

    private ConstantPool constantPool;

    public ExceptionsAttributeInfo(String attrName,
                                   int attrLen,
                                   ByteReader byteReader,
                                   ConstantPool constantPool) {
        super(attrName, attrLen);
        this.constantPool = constantPool;
        this.exceptionIndexTable = byteReader.readUint16s();
    }

    public int[] getExceptionIndexTable() {
        return exceptionIndexTable;
    }
}
