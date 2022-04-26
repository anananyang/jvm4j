package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;

public class INVOKE_SPECIAL extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().popRef();
    }
}
