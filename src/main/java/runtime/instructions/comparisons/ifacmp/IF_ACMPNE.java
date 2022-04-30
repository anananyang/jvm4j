package runtime.instructions.comparisons.ifacmp;

import runtime.instructions.base.BranchInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JObject;

public class IF_ACMPNE extends BranchInstruction {

    @Override
    protected boolean check(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        JObject val1 = stack.popRef();
        JObject val2 = stack.popRef();
        return val1 != val2;
    }
}
