package runtime.instructions.math.arithmetic.add;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class DADD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        double val1 = operandStack.popDouble();
        double val2 = operandStack.popDouble();
        double result = val2 + val1;
        operandStack.pushDouble(result);
    }
}
