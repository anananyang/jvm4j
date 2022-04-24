package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantInterfaceMethodrefInfo extends ConstantInfo {

    private short classIndex;
    private short nameAndTypeIndex;

    public ConstantInterfaceMethodrefInfo(byte tag, ByteReader reader) {
        super(tag);
        this.classIndex = reader.readUnit16();
        this.nameAndTypeIndex = reader.readUnit16();
    }

    public short getClassIndex() {
        return classIndex;
    }

    public short getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
