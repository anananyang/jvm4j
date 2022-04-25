package runtime.instructions.comparisons.ificmp;

import runtime.instructions.base.BranchInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class IF_ICMPGT extends BranchInstruction {

    @Override
    protected boolean check(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int val1 = stack.popInt();
        int val2 = stack.popInt();
        return val2 > val1;
    }
}