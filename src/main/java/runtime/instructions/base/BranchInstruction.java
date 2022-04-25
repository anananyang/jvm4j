package runtime.instructions.base;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.priv.Slot;

/**
 * 跳转指令
 */
public abstract class BranchInstruction implements Instruction {
    // 跳转偏移量
    private int offset;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        this.offset = reader.readInt16();
    }

    public void execute(Frame frame) {
        if (check(frame)) {
            branch(frame, offset);
        }
    }

    /**
     * 跳转
     *
     * @param frame
     */
    public static void branch(Frame frame, int offset) {
        JThread jThread = frame.getjThread();
        int pc = jThread.getPc();
        int nextPC = pc + offset;   // 计算跳转的位置
        frame.setNextPC(nextPC);
    }

    protected abstract boolean check(Frame frame);

}
