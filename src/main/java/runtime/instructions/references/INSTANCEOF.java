package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.ClassRef;

public class INSTANCEOF extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        // 取出对象
        OperandStack stack = frame.getOperandStack();
        JObject obj = stack.popRef();
        // 如果对象为空，则直接判断直接为false
        if (obj == null) {
            stack.pushInt(0);
            return;
        }
        ClassRef classRef = this.getClassRef(frame);
        JClass jClass = classRef.resolveClass();
        stack.pushInt(obj.isInstanceOf(jClass) ? 1 : 0);
    }

    private ClassRef getClassRef(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        JClass jClass = jMethod.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (ClassRef) constantPool.getValueByIndex(index);
    }
}
