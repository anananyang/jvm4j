package runtime.rtda.share.heap;

import classFile.MemberInfo;
import classFile.attributes.CodeAttributeInfo;
import eum.AccessFlag;
import eum.AttributeType;

public class JMethod extends JClassMember {
    private int maxLocals;
    private int maxStack;
    private byte[] code;

    public JMethod(JClass jClass, MemberInfo memberInfo) {
        super(jClass, memberInfo);
        CodeAttributeInfo codeAttributeInfo = (CodeAttributeInfo) memberInfo.getFirstAttrByType(AttributeType.Code);
        if (codeAttributeInfo != null) {
            this.maxLocals = codeAttributeInfo.getMaxLocal();
            this.maxStack = codeAttributeInfo.getMaxStack();
            this.code = codeAttributeInfo.getCode();
        }
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public byte[] getCode() {
        return code;
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

    public boolean isSynchronized() {
        return (accessFlags & AccessFlag.ACC_SYNCHRONIZED.getValue()) != 0;
    }

    public boolean isBridge() {
        return (accessFlags & AccessFlag.ACC_BRIDGE.getValue()) != 0;
    }

    public boolean isVarargs() {
        return (accessFlags & AccessFlag.ACC_VARARGS.getValue()) != 0;
    }

    public boolean isNative() {
        return (accessFlags & AccessFlag.ACC_NATIVE.getValue()) != 0;
    }

    public boolean isAbstract() {
        return (accessFlags & AccessFlag.ACC_ABSTRACT.getValue()) != 0;
    }

    public boolean isStrict() {
        return (accessFlags & AccessFlag.ACC_STRICT.getValue()) != 0;
    }

    public boolean isSynthetic() {
        return (accessFlags & AccessFlag.ACC_SYNTHETIC.getValue()) != 0;
    }


}
