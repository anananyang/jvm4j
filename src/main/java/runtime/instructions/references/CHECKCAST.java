package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.ClassRef;

public class CHECKCAST extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        // 取出对象
        OperandStack stack = frame.getOperandStack();
        JObject obj = stack.popRef();
        stack.pushRef(obj);   // 取出来之后重新入栈
        // 如果对象为空，则直接判断直接为false
        if (obj == null) {
            return;   // null 可以转换成任何类型
        }
        ClassRef classRef = this.getClassRef(frame);
        JClass jClass = classRef.resolveClass();
        if(!obj.isInstanceOf(jClass)) {
            throw new ClassCastException();
        }
    }

    private ClassRef getClassRef(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        JClass jClass = jMethod.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (ClassRef) constantPool.getValueByIndex(index);
    }
}