package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantFloatInfo extends ConstantInfo {

    private float value;

    public ConstantFloatInfo(int tag, ByteReader reader) {
        super(tag);
        int intBits = reader.readUint32();
        this.value = Float.intBitsToFloat(intBits);
    }

    public float getValue() {
        return value;
    }
}
