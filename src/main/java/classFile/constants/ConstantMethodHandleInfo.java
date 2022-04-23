package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantMethodHandleInfo extends ConstantInfo {

    private byte referenceKind;
    private short referenceIndex;

    public ConstantMethodHandleInfo(byte tag, ByteReader reader) {
        super(tag);
        this.referenceKind = reader.readUnit8();
        this.referenceIndex = reader.readUnit16();
    }
}
