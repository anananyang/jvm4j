package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantMethodTypeInfo extends ConstantInfo {

    private short descriptorIndex;


    public ConstantMethodTypeInfo(byte tag, ByteReader reader) {
        super(tag);
        this.descriptorIndex = reader.readUnit16();
    }

    public short getDescriptorIndex() {
        return descriptorIndex;
    }
}
