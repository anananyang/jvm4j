package runtime.instructions.math.bool.or;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class LOR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        long val1 = operandStack.popLong();
        long val2 = operandStack.popLong();
        long result = val1 | val2;
        operandStack.pushLong(result);
    }
}
