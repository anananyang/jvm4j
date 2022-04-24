package classFile.attributes;

import classFile.ConstantPool;
import classFile.reader.ByteReader;

/**
 * attrLen = 0
 */
public class SyntheticAttributeInfo extends AttributeInfo {

    public SyntheticAttributeInfo(String attrName,
                                  int attrLen,
                                  ByteReader byteReader,
                                  ConstantPool constantPool) {
        super(attrName, attrLen);
    }
}
