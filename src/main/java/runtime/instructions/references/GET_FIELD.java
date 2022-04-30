package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.*;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.FieldRef;

public class GET_FIELD extends Index16Instruction {


    @Override
    public void execute(Frame frame) {
        FieldRef fieldRef = getFieldRef(frame);
        JField field = fieldRef.resolveField();
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        getField(field, frame);
    }

    /**
     * 根据索引从运行时常量池中获取字段引用
     *
     * @param frame
     * @return
     */
    private FieldRef getFieldRef(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        JClass jClass = jMethod.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (FieldRef) constantPool.getValueByIndex(index);
    }

    private void getField(JField field, Frame frame) {
        int slotId = field.getSlotId();
        String descriptor = field.getDescriptor();
        OperandStack stack = frame.getOperandStack();
        // 从操作数栈中取出对象的引用
        JObject obj = stack.popRef();
        if (obj == null) {
            throw new NullPointerException();
        }
        Slots objFields = obj.getFields();
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                stack.pushInt(objFields.getInt(slotId));
                break;
            case 'F':
                stack.pushFloat(objFields.getFloat(slotId));
                break;
            case 'D':
                stack.pushDouble(objFields.getDouble(slotId));
                break;
            case 'J':
                stack.pushLong(objFields.getLong(slotId));
                break;
            case 'L':
            case '[':   // 数组
                stack.pushRef(objFields.getRef(slotId));
                break;
        }
    }
}
