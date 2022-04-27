package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.instructions.base.InvokeMethodLogic;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.MethodRef;

public class INVOKE_STATIC extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        MethodRef methodRef = this.getMethodRef(frame);
        JMethod method = methodRef.resolveMethod();
        if (!method.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        JClass jClass = method.getjClass();
        if (!jClass.isInitStarted()) {
            frame.revertNextPC();    // pc 指向上一条指令，使得下一次循环时，本条指令重新执行
            jClass.initClass(frame.getjThread());
        } else {
            InvokeMethodLogic.invokeMethod(frame, method);
        }
    }

    private MethodRef getMethodRef(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        JClass jClass = jMethod.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (MethodRef) constantPool.getValueByIndex(index);
    }

}
