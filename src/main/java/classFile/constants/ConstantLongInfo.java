package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantLongInfo extends ConstantInfo {
    private long value;

    public ConstantLongInfo(int tag, ByteReader reader) {
        super(tag);
        this.value = reader.readUint64();
    }

    public long getValue() {
        return value;
    }
}
