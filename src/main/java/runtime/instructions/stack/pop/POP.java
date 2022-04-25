package runtime.instructions.stack.pop;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

/**
 * 从操作数栈弹出一个元素，pop 指令只能用于弹出 int、float 等占用一个操作数栈位置等变量
 */
public class POP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().popSlot();
    }
}
