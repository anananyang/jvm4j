package classFile.attributes;

import classFile.ConstantPool;
import classFile.reader.ByteReader;

public class SourceFileAttributeInfo extends AttributeInfo {
    // 常量池索引
    private short sourceFileIndex;
    private ConstantPool constantPool;

    public SourceFileAttributeInfo(String attrName,
                                   int attrLen,
                                   ByteReader byteReader,
                                   ConstantPool constantPool) {
        super(attrName, attrLen);
        this.sourceFileIndex = byteReader.readUnit16();
    }

    public short getSourceFileIndex() {
        return sourceFileIndex;
    }
}
