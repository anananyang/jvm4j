package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantInvokeDynamicInfo extends ConstantInfo {
    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    public ConstantInvokeDynamicInfo(ConstantType type, ByteReader reader) {
        super(type);
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
