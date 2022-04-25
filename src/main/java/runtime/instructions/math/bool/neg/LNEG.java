package runtime.instructions.math.bool.neg;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class LNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long val = stack.popLong();
        val = -val;
        stack.pushLong(val);
    }
}
