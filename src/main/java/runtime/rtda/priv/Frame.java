package runtime.rtda.priv;

import runtime.instructions.ByteCodeReader;
import runtime.rtda.share.heap.JMethod;

/**
 * 栈帧
 */
public class Frame {
    // 局部变量表
    private LocalVariableTable localVaribleTable;
    // 操作数栈
    private OperandStack operandStack;
    // 当前线程
    private JThread jThread;
    // 当前运行的方法
    private JMethod jMethod;
    // 下一条指令的位置
    private int nextPC;
    // 字节码读取
    private ByteCodeReader byteCodeReader;

    /**
     * 本地变量表、以及操作数栈的大小是由编译器计算好的
     *
     * @param maxLocals
     * @param maxStack
     */
    public Frame(JThread jThread, int maxLocals, int maxStack) {
        this.jThread = jThread;
        this.localVaribleTable = new LocalVariableTable(maxLocals);
        this.operandStack = new OperandStack(maxStack);
    }

    public Frame(JThread jThread, JMethod jMethod) {
        this.jThread = jThread;
        this.jMethod = jMethod;
        this.byteCodeReader = new ByteCodeReader(jMethod.getCode());
        this.localVaribleTable = new LocalVariableTable(jMethod.getMaxLocals());
        this.operandStack = new OperandStack(jMethod.getMaxStack());
    }

    public LocalVariableTable getLocalVaribleTable() {
        return localVaribleTable;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public JThread getjThread() {
        return jThread;
    }

    public int getNextPC() {
        return nextPC;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }

    public JMethod getjMethod() {
        return jMethod;
    }

    public void setjMethod(JMethod jMethod) {
        this.jMethod = jMethod;
    }

    public ByteCodeReader getByteCodeReader() {
        return byteCodeReader;
    }

    public void revertNextPC() {
        // 返回到上一条指令
        this.nextPC = jThread.getPc();
    }

}
