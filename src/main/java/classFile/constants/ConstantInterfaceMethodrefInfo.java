package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantInterfaceMethodrefInfo extends ConstantMemberrefInfo {


    public ConstantInterfaceMethodrefInfo(ConstantType type, ByteReader reader) {
        super(type, reader);
    }
}
