package runtime.instructions.loads.fload;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class FLOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Load.fload(frame, 1);
    }
}
