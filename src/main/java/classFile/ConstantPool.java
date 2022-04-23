package classFile;

import classFile.constants.ConstantInfo;


/**
 * 1. 有效的常量池索引是 1 - （n - 1）。0 是无效索引
 * 2、CONSTANT_Long_info 和 CONSTANT_Double_info 各占两个位置
 */
public class ConstantPool {

    public ConstantInfo[] constants;

    public ConstantPool(ConstantInfo[] constants) {
        this.constants = constants;
    }



}
