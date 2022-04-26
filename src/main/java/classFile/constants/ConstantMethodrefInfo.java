package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantMethodrefInfo extends ConstantMemberrefInfo {

    public ConstantMethodrefInfo(ConstantType type, ByteReader reader) {
        super(type, reader);

    }
}
