package runtime.instructions.conversions.i2x;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class I2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack =  frame.getOperandStack();
        int val = stack.popInt();
        long result = Integer.valueOf(val).longValue();
        stack.pushLong(result);
    }
}
