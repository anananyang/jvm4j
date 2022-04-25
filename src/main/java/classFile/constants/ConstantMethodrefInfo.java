package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantMethodrefInfo extends ConstantInfo {

    private int classIndex;
    private int nameAndTypeIndex;

    public ConstantMethodrefInfo(int tag, ByteReader reader) {
        super(tag);
        this.classIndex = reader.readUnit16();
        this.nameAndTypeIndex = reader.readUnit16();
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
