package runtime.instructions.math.arithmetic.rem;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class DREM extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        // 除数
        double val1 = operandStack.popDouble();
        // 被除数
        double val2 = operandStack.popDouble();

        double result = val2 % val1;
        operandStack.pushDouble(result);
    }
}
