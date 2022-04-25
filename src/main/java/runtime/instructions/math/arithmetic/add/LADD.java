package runtime.instructions.math.arithmetic.add;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class LADD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        long val1 = operandStack.popLong();
        long val2 = operandStack.popLong();
        long result = val2 + val1;
        operandStack.pushLong(result);
    }
}
