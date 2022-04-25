package runtime.instructions.control;

import runtime.instructions.base.BranchInstruction;
import runtime.rtda.priv.Frame;

public class GOTO extends BranchInstruction {

    /**
     * 不做判断，直接跳转
     *
     * @param frame
     * @return
     */
    @Override
    protected boolean check(Frame frame) {
        return true;
    }
}
