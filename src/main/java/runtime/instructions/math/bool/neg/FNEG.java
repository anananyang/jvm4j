package runtime.instructions.math.bool.neg;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class FNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float val = stack.popFloat();
        val = -val;
        stack.pushFloat(val);
    }
}
