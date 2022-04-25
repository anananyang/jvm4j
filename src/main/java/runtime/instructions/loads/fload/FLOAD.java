package runtime.instructions.loads.fload;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class FLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Load.fload(frame, index);
    }
}
