package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JField;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.FieldRef;

/**
 * 给某个对象的字段设置值
 */
public class PUT_FIELD extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        FieldRef fieldRef = getFieldRef(frame);
        JField field = fieldRef.resolveField();
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        JMethod jMethod = frame.getjMethod();
        JClass curClass = jMethod.getjClass();
        JClass classOfField = field.getjClass();
        if (field.isFinal()) {
            // final 字段只能在构造函数中初始化（或者指定初始化）
            if (!isCurClassField(curClass, classOfField) || !isInitMethod(jMethod)) {
                throw new IllegalAccessError();
            }
        }
        putField(field, frame);
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

    private boolean isCurClassField(JClass a, JClass b) {
        return a == b;
    }

    private boolean isInitMethod(JMethod jMethod) {
        return "<init>".equals(jMethod.getName());
    }

    private void putField(JField jField, Frame frame) {
        int slotId = jField.getSlotId();
        String descriptor = jField.getDescriptor();
        OperandStack stack = frame.getOperandStack();
        // 值
        Object val = stack.popSlot().get();
        // 对象
        JObject obj = stack.popRef();
        if (obj == null) {
            throw new NullPointerException();
        }
        switch (descriptor) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
                obj.getFields().setInt(slotId, (int) val);
                break;
            case "F":
                obj.getFields().setFloat(slotId, (float) val);
                break;
            case "J":
                obj.getFields().setLong(slotId, (long) val);
                break;
            case "D":
                obj.getFields().setDouble(slotId, (double) val);
                break;
            case "L":
            case "[":
                obj.getFields().setRef(slotId, (JObject) val);
                break;
        }
    }
}
