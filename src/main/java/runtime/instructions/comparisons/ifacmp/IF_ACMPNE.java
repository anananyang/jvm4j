package runtime.instructions.comparisons.ifacmp;

import runtime.instructions.base.BranchInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class IF_ACMPNE extends BranchInstruction {

    @Override
    protected boolean check(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Object val1 = stack.popRef();
        Object val2 = stack.popRef();
        return val1 != val2;
    }
}
