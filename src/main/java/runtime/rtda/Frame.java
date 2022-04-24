package runtime.rtda;

/**
 * 栈帧
 */
public class Frame {
    // 局部变量表
    private LocalVariableTable localVaribleTable;
    // 操作数栈
    private OperandStack operandStack;

    /**
     * 本地变量表、以及操作数栈的大小是由编译器计算好的
     *
     * @param maxLocals
     * @param maxStack
     */
    public Frame(int maxLocals, int maxStack) {
        this.localVaribleTable = new LocalVariableTable(maxLocals);
        this.operandStack = new OperandStack(maxStack);
    }

    public LocalVariableTable getLocalVaribleTable() {
        return localVaribleTable;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }
}
