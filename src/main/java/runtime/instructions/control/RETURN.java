package runtime.instructions.control;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

public class RETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getjThread().popFrame();
    }
}
