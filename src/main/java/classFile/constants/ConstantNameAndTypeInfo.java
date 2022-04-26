package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantNameAndTypeInfo extends ConstantInfo {
    // 字段名或者方法名在常量池索引
    private int nameIndex;
    // 字段名或者方法的描述符常量池索引
    private int descriptorIndex;

    public ConstantNameAndTypeInfo(ConstantType type, ByteReader reader) {
        super(type);
        this.nameIndex = reader.readUnit16();
        this.descriptorIndex = reader.readUnit16();
    }

    public String getName() {
        return constantPool.getUtf8(nameIndex);
    }

    public String getDescriptor() {
        return constantPool.getUtf8(descriptorIndex);
    }
}
