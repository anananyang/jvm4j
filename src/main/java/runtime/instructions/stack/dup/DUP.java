package runtime.instructions.stack.dup;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.priv.Slot;

/**
 * 复制栈顶元素
 */
public class DUP extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        Slot slot = operandStack.popSlot();
        operandStack.pushSlot(slot);
        operandStack.pushSlot(slot);
    }
}
