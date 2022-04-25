package runtime.instructions.constants;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

public class NOP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        // do nothing
    }
}
