package runtime.instructions.constants;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

/**
 * 将 0 推入操作数栈栈顶
 */
public class DCONST_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushDouble(0.0);
    }
}
