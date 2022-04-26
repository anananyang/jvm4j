package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantMethodTypeInfo extends ConstantInfo {

    private int descriptorIndex;


    public ConstantMethodTypeInfo(ConstantType type, ByteReader reader) {
        super(type);
        this.descriptorIndex = reader.readUnit16();
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }
}
