package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantInvokeDynamicInfo extends ConstantInfo {
    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    public ConstantInvokeDynamicInfo(int tag, ByteReader reader) {
        super(tag);
        this.bootstrapMethodAttrIndex = reader.readUnit16();
        this.nameAndTypeIndex = reader.readUnit16();
    }

    public int getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
