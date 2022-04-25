package runtime.instructions.math.arithmetic.add;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class FADD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        float val1 = operandStack.popFloat();
        float val2 = operandStack.popFloat();
        float result = val2 + val1;
        operandStack.pushFloat(result);
    }
}