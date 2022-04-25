package runtime.instructions.loads.aload;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class ALOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Load.aload(frame, index);
    }
}
