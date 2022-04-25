package runtime.instructions.loads.lload;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class LLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Load.lload(frame, index);
    }
}
