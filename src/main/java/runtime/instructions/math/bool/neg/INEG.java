package runtime.instructions.math.bool.neg;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class INEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int val = stack.popInt();
        val = -val;
        stack.pushInt(val);
    }
}
