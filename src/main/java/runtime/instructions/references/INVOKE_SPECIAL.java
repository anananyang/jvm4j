package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.instructions.base.InvokeMethodLogic;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.MethodRef;

/**
 * 有三种方法调用会使用 INVOKE_SPECIAL
 * 1、实例的初始化方法
 * 2、调用私有方法
 * 3、使用 super 关键字调用父类的方法
 * INVOKE_SPECIAL 永远将 this 放到栈帧的局部变量表索引为 0 的位置
 */
public class INVOKE_SPECIAL extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        JClass curClass = frame.getjMethod().getjClass();
        MethodRef methodRef = getMethodRef(frame);
        JClass resolvedClass = methodRef.resolveClass();
        JMethod resolvedMethod = methodRef.resolveMethod();
        // 如果是构造器，要验证方法引用中的类与方法中的类数据是相同的
        if (isConstructor(resolvedMethod) && resolvedClass != resolvedMethod.getjClass()) {
            throw new NoSuchMethodError();
        }
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        // 从操作数栈弹出 this 指针
        JObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            throw new NullPointerException();
        }
        // 验证 protected  方法的调用权限
        if (resolvedMethod.isProtected()
                && resolvedMethod.getjClass().isSuperClassOf(curClass)
                && !resolvedMethod.getjClass().isSamePackage(curClass)
                && ref.getjClass() != curClass
                && !ref.getjClass().isSubClassOf(curClass)) {
            throw new IllegalAccessError();
        }

        JMethod methodToBeInvoked = resolvedMethod;
        // 如果是调用父类中的方法
        if (curClass.isSuper()
                && resolvedClass.isSuperClassOf(curClass)
                && !isConstructor(resolvedMethod)) {
            methodToBeInvoked = curClass.getSuperClass().lookupMethod(methodRef.getName(), methodRef.getDescriptor());
        }

        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }
        InvokeMethodLogic.invokeMethod(frame, methodToBeInvoked);
    }

    private MethodRef getMethodRef(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        JClass jClass = jMethod.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (MethodRef) constantPool.getValueByIndex(index);
    }

    private boolean isConstructor(JMethod method) {
        return "<init>".equals(method.getName());
    }

}
