package runtime.instructions.control;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.instructions.base.BranchInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class TABLE_SWITCH implements Instruction {
    // 默认情况下需要跳转的偏移量
    private int defaultOffset;
    private int low;
    private int hight;
    // 跳转的索引表，存放了 (hight - low + 1) 个索引
    private int[] jumpOffsets;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        // tableSwitch 指令后面有 0 - 3 字节的 padding，保证 defaultOffset 的地址是 4 的倍数
        reader.skipPadding();
        this.defaultOffset = reader.readInt32();
        this.low = reader.readInt32();
        this.hight = reader.readInt32();
        this.jumpOffsets = reader.readInt32s(hight - low + 1);
    }

    /**
     * 从操作数栈中弹出一个 int 类型的数，如果数在 low 和 hight 之间（包含 low、hight）则跳转
     *
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int val = stack.popInt();
        int offset = this.getOffset(val);
        // 跳转
        BranchInstruction.branch(frame, offset);
    }

    private int getOffset(int val) {
        if (val < low || val > hight) {
            return defaultOffset;
        }
        int index = val - low;
        return jumpOffsets[index];
    }
}
