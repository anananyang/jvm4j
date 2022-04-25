package runtime.instructions.loads;

import runtime.rtda.priv.Frame;

public abstract class Load {

    public static void aload(Frame frame, int index) {
        Object value = frame.getLocalVaribleTable().getRef(index);
        frame.getOperandStack().pushRef(value);
    }

    public static void dload(Frame frame, int index) {
        double value = frame.getLocalVaribleTable().getDouble(index);
        frame.getOperandStack().pushDouble(value);
    }

    public static void fload(Frame frame, int index) {
        float value = frame.getLocalVaribleTable().getFloat(index);
        frame.getOperandStack().pushFloat(value);
    }

    /**
     * 从根据 index 从局部变量表获取一个 int 类型 value，
     * 将 value 压入操作数栈
     *
     * @param frame
     * @param index
     */
    public static void iload(Frame frame, int index) {
        int value = frame.getLocalVaribleTable().getInt(index);
        frame.getOperandStack().pushInt(value);
    }

    public static void lload(Frame frame, int index) {
        long value = frame.getLocalVaribleTable().getLong(index);
        frame.getOperandStack().pushLong(value);
    }
}
