package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;

public class ConstantStringInfo extends ConstantInfo {
    // 字符串在常量池中的索引
    private int strIndex;

    public ConstantStringInfo(ConstantType type, ByteReader reader) {
        super(type);
        strIndex = reader.readUnit16();
    }

    public String getStr() {
        return constantPool.getUtf8(strIndex);
    }
}
