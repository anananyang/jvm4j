package runtime.instructions.loads.dload;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class DLOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Load.dload(frame, 1);
    }
}
