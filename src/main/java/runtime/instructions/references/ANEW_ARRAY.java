package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.ClassRef;

public class ANEW_ARRAY extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ClassRef classRef = getClassRef(frame);
        JClass jClass = classRef.resolveClass();
        // 数组长度
        OperandStack stack = frame.getOperandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }
        JClass arrayClass = jClass.getArrayClass();
        JObject array = arrayClass.newArray(count);
        stack.pushRef(array);
    }

    private ClassRef getClassRef(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        JClass jClass = jMethod.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (ClassRef) constantPool.getValueByIndex(index);
    }
}
