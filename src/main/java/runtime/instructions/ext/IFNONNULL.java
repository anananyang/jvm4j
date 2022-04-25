package runtime.instructions.ext;

import runtime.instructions.base.BranchInstruction;
import runtime.rtda.priv.Frame;

public class IFNONNULL extends BranchInstruction{
    @Override
    protected boolean check(Frame frame) {
        Object ref = frame.getOperandStack().popRef();
        return ref != null;
    }
}
