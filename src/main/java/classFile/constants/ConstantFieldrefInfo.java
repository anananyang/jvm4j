package classFile.constants;

import classFile.reader.ByteReader;

/**
 * 字段符号引用
 */
public class ConstantFieldrefInfo extends ConstantInfo {

    private int classIndex;
    private int nameAndTypeIndex;

    public ConstantFieldrefInfo(int tag, ByteReader reader) {
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
