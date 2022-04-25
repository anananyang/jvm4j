package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantMethodTypeInfo extends ConstantInfo {

    private int descriptorIndex;


    public ConstantMethodTypeInfo(byte tag, ByteReader reader) {
        super(tag);
        this.descriptorIndex = reader.readUnit16();
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }
}
