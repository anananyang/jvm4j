package runtime.instructions.constants;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

/**
 * 将 null 推入操作数栈栈顶
 */
public class ACONST_NULL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushRef(null);
    }
}
