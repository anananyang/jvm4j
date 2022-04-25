package runtime.instructions.comparisons.fcmp;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

public class FCMPG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        FCMP.fcmp(frame, true);
    }
}
