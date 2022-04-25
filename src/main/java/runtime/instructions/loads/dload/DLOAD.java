package runtime.instructions.loads.dload;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.loads.Load;
import runtime.rtda.priv.Frame;

public class DLOAD extends Index8Instruction{

    @Override
    public void execute(Frame frame) {
        Load.dload(frame, index);
    }
}
