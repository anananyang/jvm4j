package runtime.instructions.store;

import runtime.rtda.priv.Frame;

public class Store {

    public static void astore(Frame frame, int index) {
        Object value = frame.getOperandStack().popRef();
        frame.getLocalVaribleTable().setRef(index, value);
    }

    public static void dstore(Frame frame, int index) {
        double value = frame.getOperandStack().popDouble();
        frame.getLocalVaribleTable().setDouble(index, value);
    }

    public static void fstore(Frame frame, int index) {
        float value = frame.getOperandStack().popFloat();
        frame.getLocalVaribleTable().setFloat(index, value);
    }

    /**
     * 从根据 index 从局部变量表获取一个 int 类型 value，
     * 将 value 压入操作数栈
     *
     * @param frame
     * @param index
     */
    public static void istore(Frame frame, int index) {
        int value = frame.getOperandStack().popInt();
        frame.getLocalVaribleTable().setInt(index, value);
    }

    public static void lstore(Frame frame, int index) {
        long value = frame.getOperandStack().popLong();
        frame.getLocalVaribleTable().setLong(index, value);
    }
}
