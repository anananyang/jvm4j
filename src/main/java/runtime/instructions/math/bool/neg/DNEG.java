package runtime.instructions.math.bool.neg;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class DNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double val = stack.popDouble();
        val = -val;
        stack.pushDouble(val);
    }
}
