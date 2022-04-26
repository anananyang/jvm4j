package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantFloatInfo extends ConstantInfo {

    private float value;

    public ConstantFloatInfo(ConstantType type, ByteReader reader) {
        super(type);
        int intBits = reader.readUint32();
        this.value = Float.intBitsToFloat(intBits);
    }

    public float getValue() {
        return value;
    }
}
