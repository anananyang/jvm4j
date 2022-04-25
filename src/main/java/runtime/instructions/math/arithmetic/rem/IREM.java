package runtime.instructions.math.arithmetic.rem;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

/**
 * 取余数
 */
public class IREM extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        // 除数
        int val1 = operandStack.popInt();
        // 被除数
        int val2 = operandStack.popInt();
        if(isZero(val1)) {
            throw new RuntimeException("java.lang.ArithmeticException: / by zero");
        }
        int result = val2 % val1;
        operandStack.pushInt(result);
    }

    private boolean isZero(int val) {
        return val == 0;
    }
}
