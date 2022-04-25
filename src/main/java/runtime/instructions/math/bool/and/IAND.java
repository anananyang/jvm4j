package runtime.instructions.math.bool.and;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class IAND extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int val1 = operandStack.popInt();
        int val2 = operandStack.popInt();
        int result = val1 & val2;
        operandStack.pushInt(result);
    }
}
