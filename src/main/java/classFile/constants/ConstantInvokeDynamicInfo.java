package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantInvokeDynamicInfo extends ConstantInfo {
    private short bootstrapMethodAttrIndex;
    private short nameAndTypeIndex;

    public ConstantInvokeDynamicInfo(byte tag, ByteReader reader) {
        super(tag);
        this.bootstrapMethodAttrIndex = reader.readUnit16();
        this.nameAndTypeIndex = reader.readUnit16();
    }

    public short getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public short getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
