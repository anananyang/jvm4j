package runtime.instructions.loads.iload;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class ILOAD_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Load.iload(frame, 2);
    }
}
