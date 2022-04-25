package runtime.instructions.comparisons.lcmp;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

/**
 * 从栈顶弹出两个 long 类型变量进行比较，然后把比较结果推入栈顶
 */
public class LCMP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long val1 = stack.popLong();
        long val2 = stack.popLong();
        stack.pushInt(compare(val1, val2));
    }

    private int compare(long val1, long val2) {
        if (val1 < val2) {
            return 1;
        } else if (val1 > val2) {
            return -1;
        }
        return 0;
    }
}
