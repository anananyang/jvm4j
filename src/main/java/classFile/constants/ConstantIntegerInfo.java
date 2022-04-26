package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantIntegerInfo extends ConstantInfo {
    private int value;

    public ConstantIntegerInfo(ConstantType type, ByteReader reader) {
        super(type);
        this.value = reader.readUint32();
    }

    public int getValue() {
        return value;
    }
}
