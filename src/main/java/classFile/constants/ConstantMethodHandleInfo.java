package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantMethodHandleInfo extends ConstantInfo {

    private int referenceKind;
    private int referenceIndex;

    public ConstantMethodHandleInfo(ConstantType type, ByteReader reader) {
        super(type);
        this.referenceKind = reader.readUnit8();
        this.referenceIndex = reader.readUnit16();
    }

    public int getReferenceKind() {
        return referenceKind;
    }

    public int getReferenceIndex() {
        return referenceIndex;
    }
}
