package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantMemberrefInfo extends ConstantInfo {

    protected int classIndex;
    protected int nameAndTypeIndex;

    public ConstantMemberrefInfo(ConstantType type, ByteReader reader) {
        super(type);
        this.classIndex = reader.readUnit16();
        this.nameAndTypeIndex = reader.readUnit16();
    }

    public String getClassName() {
        return constantPool.getClassName(classIndex);
    }

    public String getName() {
        return constantPool.getName(nameAndTypeIndex);
    }

    public String getDescriptor() {
        return constantPool.getType(nameAndTypeIndex);
    }
}
