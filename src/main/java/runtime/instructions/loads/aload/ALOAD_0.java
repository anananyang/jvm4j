package runtime.instructions.loads.aload;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class ALOAD_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Load.aload(frame, 0);
    }
}
