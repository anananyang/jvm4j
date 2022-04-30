package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JField;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.Slots;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.FieldRef;

/**
 * 取出静态变量的值，并推入操作数栈的栈顶
 */
public class GET_STATIC extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        FieldRef fieldRef = getFieldRef(frame);
        JField field = fieldRef.resolveField();
        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        JClass fieldClass = field.getjClass();
        // 判断类是否已经初始化
        if (!fieldClass.isInitStarted()) {
            frame.revertNextPC();    // pc 指向上一条指令，使得下一次循环时，本条指令重新执行
            fieldClass.initClass(frame.getjThread());
        } else {
            getStatic(field, frame);
        }

    }

    private void getStatic(JField field, Frame frame) {
        Slots slots = field.getjClass().getStaticVars();
        int slotId = field.getSlotId();
        OperandStack stack = frame.getOperandStack();
        String descriptor = field.getDescriptor();
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                stack.pushInt(slots.getInt(slotId));
                break;
            case 'F':
                stack.pushFloat(slots.getFloat(slotId));
                break;
            case 'D':
                stack.pushDouble(slots.getDouble(slotId));
                break;
            case 'J':
                stack.pushLong(slots.getLong(slotId));
                break;
            case 'L':
            case '[':   // 数组
                stack.pushRef(slots.getRef(slotId));
                break;
        }
    }

    private FieldRef getFieldRef(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        JClass jClass = jMethod.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (FieldRef) constantPool.getValueByIndex(index);
    }
}
