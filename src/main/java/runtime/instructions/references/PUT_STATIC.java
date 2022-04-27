package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JField;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.Slots;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.FieldRef;

/**
 * 给某个静态变量赋值
 */
public class PUT_STATIC extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        FieldRef fieldRef = this.getFieldRef(frame);
        JField jField = fieldRef.resolveField();
        if (!jField.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        JMethod jMethod = frame.getjMethod();
        JClass curClass = jMethod.getjClass();
        JClass fieldClass = jField.getjClass();
        if (jField.isFinal()) {
            if (!isCurClassField(curClass, fieldClass) || !isInitMethod(jMethod)) {
                throw new IllegalAccessError();
            }
        }

        if (!fieldClass.isInitStarted()) {
            frame.revertNextPC();    // pc 指向上一条指令，使得下一次循环时，本条指令重新执行
            fieldClass.initClass(frame.getjThread());
        } else {
            putStatic(jField, frame);
        }


    }

    private FieldRef getFieldRef(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        JClass jClass = jMethod.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (FieldRef) constantPool.getValueByIndex(index);
    }

    private boolean isCurClassField(JClass a, JClass b) {
        return a == b;
    }

    private boolean isInitMethod(JMethod jMethod) {
        return "<clinit>".equals(jMethod.getName());
    }

    /**
     * 将操作数栈的栈顶元素设置到指定的 slot 中
     *
     * @param jField
     * @param frame
     */
    private void putStatic(JField jField, Frame frame) {
        Slots slots = jField.getjClass().getStaticVars();
        int slotId = jField.getSlotId();
        // 字段的类型
        String descriptor = jField.getDescriptor();
        switch (descriptor) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
                slots.setInt(slotId, frame.getOperandStack().popInt());
                break;
            case "F":
                slots.setFloat(slotId, frame.getOperandStack().popFloat());
                break;
            case "J":
                slots.setLong(slotId, frame.getOperandStack().popLong());
                break;
            case "D":
                slots.setDouble(slotId, frame.getOperandStack().popDouble());
                break;
            case "L":
                slots.setRef(slotId, frame.getOperandStack().popRef());
                break;
        }
    }
}
