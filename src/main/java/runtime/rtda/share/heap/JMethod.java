package runtime.rtda.share.heap;

import classFile.MemberInfo;
import classFile.attributes.CodeAttributeInfo;
import eum.AccessFlag;
import eum.AttributeType;

public class JMethod extends JClassMember {
    private int maxLocals;
    private int maxStack;
    private byte[] code;
    private int argSlotCount;
    private MethodDescriptor methodDescriptor;

    public JMethod(JClass jClass, MemberInfo memberInfo) {
        super(jClass, memberInfo);
        this.methodDescriptor = new MethodDescriptor(memberInfo.getDescriptor());
        this.argSlotCount = calcArgsCount();
        // 调用 super 构造器设置过 accessFlag
        if (isNative()) {
            this.injectCodeAttribute();
        } else {
            this.copyCodeAttribute(memberInfo);
        }
    }

    private void injectCodeAttribute() {
        this.maxLocals = this.argSlotCount;
        this.maxStack = 4;
        byte[] code = null;
        switch (methodDescriptor.getReturnType().charAt(0)) {
            // return
            case 'V':
                code = new byte[]{(byte) 0xfe, (byte) 0xb1};
                break;
            // dreturn
            case 'D':
                code = new byte[]{(byte) 0xfe, (byte) 0xaf};
                break;
            // freturn
            case 'F':
                code = new byte[]{(byte) 0xfe, (byte) 0xae};
                break;
            // freturn
            case 'J':
                code = new byte[]{(byte) 0xfe, (byte) 0xad};
                break;
            case '[':
            // areturn
            case 'L':
                code = new byte[]{(byte) 0xfe, (byte) 0xb0};
                break;
            // ireturn
            default:
                code = new byte[]{(byte) 0xfe, (byte) 0xac};
                break;
        }
        this.code = code;
    }

    private void copyCodeAttribute(MemberInfo memberInfo) {
        CodeAttributeInfo codeAttributeInfo = (CodeAttributeInfo) memberInfo.getFirstAttrByType(AttributeType.Code);
        if (codeAttributeInfo == null) {
            return;
        }
        this.maxLocals = codeAttributeInfo.getMaxLocal();
        this.maxStack = codeAttributeInfo.getMaxStack();
        this.code = codeAttributeInfo.getCode();
    }

    private int calcArgsCount() {
        int count = methodDescriptor.getParamCount();
        // 不是静态，会有一个 this 指针
        if (!isStatic()) {
            count++;
        }
        return count;
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

    public int getArgSlotCount() {
        return argSlotCount;
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
