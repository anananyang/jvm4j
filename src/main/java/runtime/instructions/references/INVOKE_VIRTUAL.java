package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.instructions.base.InvokeMethodLogic;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.MethodRef;

public class INVOKE_VIRTUAL extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        JClass curClass = frame.getjMethod().getjClass();
        MethodRef methodRef = this.getMethodRef(frame);
        JMethod resolvedMethod = methodRef.resolveMethod();
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        JObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            // hack
            if("println".equals(methodRef.getName())) {
                printMsg(methodRef, frame);
                return;
            }
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

        // 动态绑定（寻找最终的方法）
        JMethod methodToBeInvoked = ref.getjClass().lookupMethod(methodRef.getName(), methodRef.getDescriptor());

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

    private void printMsg(MethodRef methodRef, Frame frame) {
        OperandStack stack = frame.getOperandStack();
        switch (methodRef.getDescriptor()) {
            case "(Z)V":
            case "(C)V":
            case "(B)V":
            case "(S)V":
            case "(I)V":
                System.out.println(stack.popInt());
                break;
            case "(J)V":
                System.out.println(stack.popLong());
                break;
            case "(F)V":
                System.out.println(stack.popFloat());
                break;
            case "(D)V":
                System.out.println(stack.popDouble());
                break;
            default:
                System.out.println("println: " + methodRef.getDescriptor());
                stack.popRef();

        }
    }
}
