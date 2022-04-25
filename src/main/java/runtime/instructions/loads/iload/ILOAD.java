package runtime.instructions.loads.iload;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class ILOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Load.iload(frame, index);
    }
}