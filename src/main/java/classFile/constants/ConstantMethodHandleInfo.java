package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantMethodHandleInfo extends ConstantInfo {

    private int referenceKind;
    private int referenceIndex;

    public ConstantMethodHandleInfo(int tag, ByteReader reader) {
        super(tag);
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
