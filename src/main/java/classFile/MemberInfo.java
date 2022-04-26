package classFile;

import classFile.attributes.AttributeInfo;
import eum.AttributeType;
import classFile.reader.AttributeReader;
import classFile.reader.ByteReader;

public class MemberInfo {
    private ConstantPool constantPool;
    private int accessFlag;
    private int nameIndex;
    private int descriptorIndex;
    private AttributeInfo[] attributes;

    public MemberInfo(ByteReader byteReader, ConstantPool constantPool) {
        this.constantPool = constantPool;
        this.accessFlag = byteReader.readUnit16();
        this.nameIndex = byteReader.readUnit16();
        this.descriptorIndex = byteReader.readUnit16();
        this.attributes = AttributeReader.read(byteReader, constantPool);
    }

    public int getAccessFlag() {
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

    public AttributeInfo getFirstAttrByType(AttributeType attributeType) {
        if (isAttributesEmpty()) {
            return null;
        }
        String name = attributeType.name();
        for (AttributeInfo attribute : attributes) {
            if (name.equals(attribute.getAttrName())) {
                return attribute;
            }
        }
        return null;
    }

    private boolean isAttributesEmpty() {
        return attributes == null || attributes.length == 0;
    }

}
