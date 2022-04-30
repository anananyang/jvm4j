package runtime.instructions.math.shift;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class LUSHR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        // 移位的位数，long 只有 64 位，所以取最低的 6 位就够了，
        int val1 = operandStack.popInt();
        long val2 = operandStack.popLong();
        long result = val2 >>> (val1 & 0x3f);
        operandStack.pushLong(result);
    }
}
