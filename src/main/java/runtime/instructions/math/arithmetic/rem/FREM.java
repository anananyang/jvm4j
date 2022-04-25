package runtime.instructions.math.arithmetic.rem;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class FREM extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        // 除数
        float val1 = operandStack.popFloat();
        // 被除数
        float val2 = operandStack.popFloat();

        float result = val2 % val1;
        operandStack.pushFloat(result);
    }
}