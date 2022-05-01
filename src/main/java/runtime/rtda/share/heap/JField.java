package runtime.rtda.share.heap;

import classFile.MemberInfo;
import classFile.attributes.ConstantValueAttributeInfo;
import eum.AccessFlag;
import eum.AttributeType;
import util.AttributeUtil;

import static eum.AttributeType.ConstantValue;

public class JField extends JClassMember {
    // 该字段在 Slots 的索引
    private int slotId;
    // 常量值在运行时常量池的索引, 类加载时使用
    private int constValueIndex;

    public JField(JClass jClass, MemberInfo memberInfo) {
        super(jClass, memberInfo);
        ConstantValueAttributeInfo attributeInfo = (ConstantValueAttributeInfo) AttributeUtil.getFirstAttrByType(ConstantValue, memberInfo.getAttributes());
        if (attributeInfo != null) {
            this.constValueIndex = attributeInfo.getConstantValueIndex();
        }

    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getSlotId() {
        return slotId;
    }

    public int getConstValueIndex() {
        return constValueIndex;
    }

    public boolean isPulic() {
        return (accessFlags & AccessFlag.ACC_PUBLIC.getValue()) != 0;
    }

    public boolean isPrivate() {
        return (accessFlags & AccessFlag.ACC_PRIVATE.getValue()) != 0;
    }

    public boolean isProtected() {
        return (accessFlags & AccessFlag.ACC_PROTECTED.getValue()) != 0;
    }

    public boolean isStatic() {
        return (accessFlags & AccessFlag.ACC_STATIC.getValue()) != 0;
    }

    public boolean isFinal() {
        return (accessFlags & AccessFlag.ACC_FINAL.getValue()) != 0;
    }

    public boolean isVolatile() {
        return (accessFlags & AccessFlag.ACC_VOLATILE.getValue()) != 0;
    }

    public boolean isTransient() {
        return (accessFlags & AccessFlag.ACC_TRANSIENT.getValue()) != 0;
    }

    public boolean isSynthetic() {
        return (accessFlags & AccessFlag.ACC_SYNTHETIC.getValue()) != 0;
    }

    public boolean isEnum() {
        return (accessFlags & AccessFlag.ACC_ENUM.getValue()) != 0;
    }

    public boolean isAccessibleTo(JClass other) {
        // 公共访问权限
        if (isPulic()) {
            return true;
        }
        // prtected 权限：子类能访问、在相同的包下能访问，或者就是同一个类
        JClass jClass = this.jClass;
        if (isProtected()) {
            return other.isSubClassOf(jClass) || jClass.isSamePackage(other) || other == jClass;
        }
        // 包访问权限
        if (!isPrivate()) {
            return jClass.isSamePackage(other);
        }
        // 私有访问权限，只有统一个类能访问
        return jClass == other;
    }


}
