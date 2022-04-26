package classFile.constants;

import classFile.ConstantPool;
import classFile.reader.ByteReader;
import eum.ConstantType;

/**
 * 类和超类索引、以及接口表中的接口索引指向的都是 ConstantClassInfo
 */
public class ConstantClassInfo extends ConstantInfo {

    // 类名在常量池中的索引
    private int classNameIdx;


    public ConstantClassInfo(ConstantType type, ByteReader reader) {
        super(type);
        this.classNameIdx = reader.readUnit16();
    }

    public String getClassName() {
        ConstantUtf8Info utf8Info = (ConstantUtf8Info)constantPool.getByIndex(classNameIdx);
        return utf8Info.getValue();
    }
}
