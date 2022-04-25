package runtime.instructions.conversions.l2x;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class L2I extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack =  frame.getOperandStack();
        long val = stack.popLong();
        int result = Long.valueOf(val).intValue();
        stack.pushInt(result);
    }
}

