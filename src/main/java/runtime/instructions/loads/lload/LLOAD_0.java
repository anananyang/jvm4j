package runtime.instructions.loads.lload;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class LLOAD_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Load.lload(frame, 0);
    }
}
