package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantDoubleInfo extends ConstantInfo {

    private double value;

    public ConstantDoubleInfo(ConstantType type, ByteReader reader) {
        super(type);
        long longBits = reader.readUint64();
        this.value = Double.longBitsToDouble(longBits);
    }

    public double getValue() {
        return value;
    }
}
