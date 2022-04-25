package runtime.instructions.comparisons.dcmp;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

public class DCMPL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        DCMP.dcmp(frame, false);
    }
}
