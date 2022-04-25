package runtime.instructions.math.arithmetic.mul;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

/**
 * 取余数
 */
public class IMUL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int val1 = operandStack.popInt();
        int val2 = operandStack.popInt();
        int result = val2 * val1;
        operandStack.pushInt(result);
    }
}
