package runtime.instructions.store;

import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JObject;

public class Store {

    public static void astore(Frame frame, int index) {
        JObject value = frame.getOperandStack().popRef();
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
     *  从操作数栈弹出一个 int 类型 value，
     * 将 value 设置到局部变量表
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
