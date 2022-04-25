package runtime.instructions.stack.pop;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;

public class POP2 extends NoOperandsInstruction {

    /**
     * double和long变量在操作数栈中占据两个位置，需要使用pop2指令弹出。
     * 但是我的实现中。double 和 long 还是占用一个位置，所以只需 pop 一次。
     *
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().popSlot();
    }
}
