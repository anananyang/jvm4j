package classFile.constants;

import classFile.reader.ByteReader;

public class ConstantStringInfo extends ConstantInfo {
    // 字符串在常量池中的索引
    private short strIndex;

    public ConstantStringInfo(byte tag, ByteReader reader) {
        super(tag);
        strIndex = reader.readUnit16();
    }

}
