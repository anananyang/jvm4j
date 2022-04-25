package runtime.instructions.constants;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.rtda.priv.Frame;

public class BIPUSH implements Instruction {

    private int operand;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        operand = reader.readInt8();
    }

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushInt(operand);
    }
}
