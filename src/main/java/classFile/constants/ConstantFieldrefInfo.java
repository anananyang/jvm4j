package classFile.constants;

import classFile.reader.ByteReader;

/**
 * 字段符号引用
 */
public class ConstantFieldrefInfo extends ConstantInfo {

    private short classIndex;
    private short nameAndTypeIndex;

    public ConstantFieldrefInfo(byte tag, ByteReader reader) {
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
