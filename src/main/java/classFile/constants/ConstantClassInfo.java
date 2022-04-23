package classFile.constants;

import classFile.reader.ByteReader;

/**
 * 类和超类索引、以及接口表中的接口索引指向的都是 ConstantClassInfo
 */
public class ConstantClassInfo extends ConstantInfo {

    // 类名在常量池中的索引
    private short classNameIdx;

    public ConstantClassInfo(byte tag, ByteReader reader) {
        super(tag);
        this.classNameIdx = reader.readUnit16();
    }
}
