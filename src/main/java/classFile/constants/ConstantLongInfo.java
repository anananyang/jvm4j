package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantLongInfo extends ConstantInfo {
    private long value;

    public ConstantLongInfo(ConstantType type, ByteReader reader) {
        super(type);
        this.value = reader.readUint64();
    }

    public long getValue() {
        return value;
    }
}
