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
        this.constants = constants;
    }

    public ConstantInfo getByIndex(int index) {
        if (index == 0 || index >= constants.length) {
            throw new RuntimeException("Invalid constant pool index!");
        }
        return constants[index];
    }

    public int getconstantoCount() {
        return constants.length;
    }

    public String getUtf8(int index) {
        ConstantUtf8Info constant = (ConstantUtf8Info) getByIndex(index);
        return constant.getValue();
    }

    public String getName(int index) {
        ConstantNameAndTypeInfo constant = (ConstantNameAndTypeInfo) getByIndex(index);
        short nameInde = constant.getNameIndex();
        return getUtf8(nameInde);
    }

    public String getType(int index) {
        ConstantNameAndTypeInfo constant = (ConstantNameAndTypeInfo) getByIndex(index);
        short descriptorIndex = constant.getDescriptorIndex();
        return getUtf8(descriptorIndex);
    }

    public String[] getNameAndType(int index) {
        ConstantNameAndTypeInfo constant = (ConstantNameAndTypeInfo) getByIndex(index);
        short nameIndex = constant.getNameIndex();
        short descriptorIndex = constant.getDescriptorIndex();
        String[] arr = {
                getUtf8(nameIndex),
                getUtf8(descriptorIndex)
        };
        return arr;
    }

    public String getClassName(int index) {
        ConstantClassInfo constant = (ConstantClassInfo) getByIndex(index);
        short classNameIndex = constant.getClassNameIdx();
        return getUtf8(classNameIndex);
    }

}
