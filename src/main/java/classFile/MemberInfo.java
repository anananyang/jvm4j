package classFile;

import classFile.attributes.AttributeInfo;
import classFile.reader.AttributeReader;
import classFile.reader.ByteReader;

public class MemberInfo {
    private ConstantPool constantPool;
    private short accessFlag;
    private short nameIndex;
    private short descriptorIndex;
    private AttributeInfo[] attributes;

    public MemberInfo(ByteReader byteReader, ConstantPool constantPool) {
        this.constantPool = constantPool;
        this.accessFlag = byteReader.readUnit16();
        this.nameIndex = byteReader.readUnit16();
        this.descriptorIndex = byteReader.readUnit16();
        this.attributes = AttributeReader.read(byteReader, constantPool);
    }

    public short getAccessFlag() {
        return accessFlag;
    }

    public String getName() {
        return constantPool.getUtf8(nameIndex);
    }

    public String getDescriptor() {
        return constantPool.getUtf8(descriptorIndex);
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }
}
