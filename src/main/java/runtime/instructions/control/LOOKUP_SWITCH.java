package runtime.instructions.control;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.instructions.base.BranchInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class LOOKUP_SWITCH implements Instruction {

    private int defaultOffset;
    private int npairs;
    // 两个 int 表示一对，第一个 int 表示 case 的 key，第二个 int 表示跳转的位置
    private int[] matchOffsets;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        reader.skipPadding();
        this.defaultOffset = reader.readInt32();
        this.npairs = reader.readInt32();
        this.matchOffsets = reader.readInt32s(npairs * 2);
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int key = stack.popInt();
        int offset = this.getOffset(key);
        // 跳转
        BranchInstruction.branch(frame, offset);
    }

    private int getOffset(int key) {
        for (int i = 0; i < matchOffsets.length; i = i + 2) {
            if (key == matchOffsets[i]) {
                return matchOffsets[i + 1];
            }
        }
        return defaultOffset;
    }
}
