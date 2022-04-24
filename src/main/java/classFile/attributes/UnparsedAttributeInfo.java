package classFile.attributes;

import classFile.ConstantPool;
import classFile.reader.ByteReader;

public class UnparsedAttributeInfo extends AttributeInfo {

    private byte[] bytes;

    public UnparsedAttributeInfo(String attrName,
                                 int attrLen,
                                 ByteReader byteReader,
                                 ConstantPool constantPool) {
        super(attrName, attrLen);
        bytes = byteReader.readBytes(attrLen);
    }

}
