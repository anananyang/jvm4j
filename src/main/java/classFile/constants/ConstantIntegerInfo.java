package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantIntegerInfo extends ConstantInfo {
    private int value;

    public ConstantIntegerInfo(int tag, ByteReader reader) {
        super(tag);
        this.value = reader.readUint32();
    }

    public int getValue() {
        return value;
    }
}
