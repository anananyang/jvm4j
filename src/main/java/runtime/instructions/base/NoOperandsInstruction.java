package runtime.instructions.base;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.rtda.priv.Frame;

/**
 * nop 指令
 */
public abstract class NoOperandsInstruction implements Instruction {

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        // 该指令没有操作数
    }

    @Override
    public abstract void execute(Frame frame);
}
