package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

/**
 * 字段符号引用
 */
public class ConstantFieldrefInfo extends ConstantMemberrefInfo {


    public ConstantFieldrefInfo(ConstantType type, ByteReader reader) {
        super(type, reader);
    }

}
