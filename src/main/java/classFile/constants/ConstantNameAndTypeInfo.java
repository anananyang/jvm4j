package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantNameAndTypeInfo extends ConstantInfo {
    // 字段名或者方法名在常量池索引
    private short nameIndex;
    // 字段名或者方法的描述符常量池索引
    private short descriptorIndex;

    public ConstantNameAndTypeInfo(byte tag, ByteReader reader) {
        super(tag);
        this.nameIndex = reader.readUnit16();
        this.descriptorIndex = reader.readUnit16();
    }

    public short getNameIndex() {
        return nameIndex;
    }

    public short getDescriptorIndex() {
        return descriptorIndex;
    }
}
