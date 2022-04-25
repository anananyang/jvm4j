package runtime.instructions.constants;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

public class LCONST_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushLong(1l);
    }
}
