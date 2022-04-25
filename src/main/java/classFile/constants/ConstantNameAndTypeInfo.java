package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantNameAndTypeInfo extends ConstantInfo {
    // 字段名或者方法名在常量池索引
    private int nameIndex;
    // 字段名或者方法的描述符常量池索引
    private int descriptorIndex;

    public ConstantNameAndTypeInfo(int tag, ByteReader reader) {
        super(tag);
        this.nameIndex = reader.readUnit16();
        this.descriptorIndex = reader.readUnit16();
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }
}
