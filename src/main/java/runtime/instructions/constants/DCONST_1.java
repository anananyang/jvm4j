package runtime.instructions.constants;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

public class DCONST_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushDouble(1.0);
    }
}
