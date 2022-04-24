package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantLongInfo extends ConstantInfo {
    private long value;

    public ConstantLongInfo(byte tag, ByteReader reader) {
        super(tag);
        this.value = reader.readUint64();
    }

    public long getValue() {
        return value;
    }
}
