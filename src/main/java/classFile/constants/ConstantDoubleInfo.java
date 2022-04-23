package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantDoubleInfo extends ConstantInfo {

    private double value;

    public ConstantDoubleInfo(byte tag, ByteReader reader) {
        super(tag);
        long longBits = reader.readUint64();
        this.value = Double.longBitsToDouble(longBits);
    }
}
