package runtime.instructions.ext;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.instructions.base.BranchInstruction;
import runtime.rtda.priv.Frame;

/**
 * goto_w 和 goto 最大的不同就是 offset 从两个字节变成了四个字节
 */
public class GOTO_W implements Instruction {
    private int offset;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        this.offset = reader.readInt32();
    }

    @Override
    public void execute(Frame frame) {
        BranchInstruction.branch(frame, offset);
    }
}
