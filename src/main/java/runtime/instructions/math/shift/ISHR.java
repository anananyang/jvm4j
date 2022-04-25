package runtime.instructions.math.shift;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class ISHR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        // 移位的位数，int 只有 32 位，所以取最低的 5 位就够了，
        int val1 = operandStack.popInt();
        int val2 = operandStack.popInt();
        int result = val2 >> (val1 & 0x1f);
        operandStack.pushInt(result);
    }
}
