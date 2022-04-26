package classFile.constants;

import classFile.ConstantPool;
import eum.ConstantType;

public abstract class ConstantInfo {
    protected ConstantType type;
    protected ConstantPool constantPool;

    ConstantInfo(ConstantType type) {
        this.type = type;
    }

    public int getTag() {
        return type.getTag();
    }

    public ConstantType getType() {
        return type;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }
}