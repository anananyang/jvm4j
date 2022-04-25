package runtime.instructions.conversions.f2x;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class F2D extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack =  frame.getOperandStack();
        float val = stack.popFloat();
        double result = Float.valueOf(val).doubleValue();
        stack.pushDouble(result);
    }
}
