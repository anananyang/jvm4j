package classFile;

import classFile.constants.ConstantClassInfo;
import classFile.constants.ConstantInfo;
import classFile.constants.ConstantNameAndTypeInfo;
import classFile.constants.ConstantUtf8Info;


/**
 * 1. 有效的常量池索引是 1 - （n - 1）。0 是无效索引
 * 2、CONSTANT_Long_info 和 CONSTANT_Double_info 各占两个位置
 */
public class ConstantPool {

    public ConstantInfo[] constants;

    public ConstantPool(ConstantInfo[] constants) {
        if (constants != null) {
            // 常量池从 1 开始
            for (int i = 1; i < constants.length; i++) {
                // long 和 double 占了两个位置，第二个位置为 null
                if (constants[i] != null) {
                    constants[i].setConstantPool(this);
                }

            }
        }
        this.constants = constants;
    }

    public ConstantInfo getByIndex(int index) {
        if (index == 0 || index >= constants.length) {
            throw new RuntimeException("Invalid constant pool index!");
        }
        return constants[index];
    }

    public int getconstantoCount() {
        return constants == null ? 0 : constants.length;
    }

    public String getUtf8(int index) {
        ConstantUtf8Info constant = (ConstantUtf8Info) getByIndex(index);
        return constant.getValue();
    }

    public String getName(int index) {
        ConstantNameAndTypeInfo constant = (ConstantNameAndTypeInfo) getByIndex(index);
        return constant.getName();
    }

    public String getType(int index) {
        ConstantNameAndTypeInfo constant = (ConstantNameAndTypeInfo) getByIndex(index);
        ;
        return constant.getDescriptor();
    }

    public String[] getNameAndType(int index) {
        ConstantNameAndTypeInfo constant = (ConstantNameAndTypeInfo) getByIndex(index);
        String[] arr = {
                constant.getName(),
                constant.getDescriptor()
        };
        return arr;
    }

    public String getClassName(int index) {
        ConstantClassInfo constant = (ConstantClassInfo) getByIndex(index);
        return constant.getClassName();
    }

    public ConstantInfo[] getConstants() {
        return constants;
    }
}
