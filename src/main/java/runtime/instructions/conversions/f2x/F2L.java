package runtime.instructions.conversions.f2x;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class F2L extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack =  frame.getOperandStack();
        float val = stack.popFloat();
        long result = Float.valueOf(val).longValue();
        stack.pushLong(result);
    }
}