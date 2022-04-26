package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.MethodRef;

public class INVOKE_VIRTUAL extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        MethodRef methodRef = this.getMethodRef(frame);
        if ("println".equals(methodRef.getName())) {
            printMsg(methodRef, frame);
        }
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
                stack.popRef();
                throw new RuntimeException(methodRef.getDescriptor());
        }
    }
}
