package runtime.instructions.math.arithmetic.rem;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class LREM extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        // 除数
        long val1 = operandStack.popLong();
        // 被除数
        long val2 = operandStack.popLong();
        if(isZero(val1)) {
            throw new RuntimeException("java.lang.ArithmeticException: / by zero");
        }
        long result = val2 % val1;
        operandStack.pushLong(result);
    }

    private boolean isZero(long val) {
        return val == 0;
    }
}
